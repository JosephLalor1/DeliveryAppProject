//Created by: Joseph Lalor
//Student Number: c00312883
//Date: 20/04/2026
//Purpose: Tracking panel for order

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class TrackingPanel extends JPanel 
    {
        private int[][] mapPoints = new int[19][19];
        private int nodeCount = 18;
        private int[][] nodePositions;
        private int[][] edges = 
            {
                {0, 1, 4},  // node 0 -> node 1, weight 4
                {1, 2, 3},  //node 1
                {1, 15, 4}, //node 2
                {2, 12, 8}, //node 3
                {2, 3, 3},  //node 4
                {3, 4, 2},  //node 5
                {4, 6, 5},  //node 6
                {6, 5, 12}, //node 7
                {6, 7, 11}, //node 8
                {6, 8, 9},  //node 9
                {8, 11, 7}, //node 10
                {10, 11, 10},//node 11
                {8, 9, 8},  //node 12
                {12, 13, 3},    //node 13
                {13, 14, 2},    //node 14
                {15, 16, 5},    //node 15
                {16, 17, 7},    //node 16
                {16, 18, 6}     //node 17
            };

        private int destinationNode = -1;   // -1 means no active order
        private int activeOrderId = -1;
        private int secondsRemaining = 0;
        private Timer countdownTimer;
        private Timer animationTimer;
        private JLabel statusLabel;

        // Animation state
        private List<Integer> path;             // node sequence from source to destination
        private int totalDistance = 0;          // sum of edge weights along path
        private long animationStartMillis = 0;  // when the animation began
        private double progressDistance = 0;    // how far along the path (in weight units) we are

        public TrackingPanel()
            {
                for (int[] edge : edges) 
                    {
                        mapPoints[edge[0]][edge[1]] = edge[2];
                        mapPoints[edge[1]][edge[0]] = edge[2];
                    }
                this.nodePositions = new int[nodeCount][2];
                
                // Arrange nodes in a grid
                int cols = (int) Math.ceil(Math.sqrt(nodeCount));
                int marginX = 75, marginY = 75;
                int spacingX = 120, spacingY = 120;
                for (int i = 0; i < nodeCount; i++)
                    {
                        int row = i / cols;
                        int col = i % cols;
                        nodePositions[i][0] = marginX + col * spacingX;
                        nodePositions[i][1] = marginY + row * spacingY;
                    }

                this.setLayout(new BorderLayout());
                statusLabel = new JLabel("No active order", SwingConstants.CENTER);
                statusLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
                this.add(statusLabel, BorderLayout.SOUTH);

                loadActiveOrder();
            }

        // Fetches the active undelivered order and starts the countdown if one exists.
        public void loadActiveOrder()
            {
                // Stop any existing timers / reset state before starting fresh
                if (countdownTimer != null && countdownTimer.isRunning()) countdownTimer.stop();
                if (animationTimer != null && animationTimer.isRunning()) animationTimer.stop();
                path = null;
                progressDistance = 0;
                destinationNode = -1;
                activeOrderId = -1;
                repaint();

                int restaurantId = -1;
                try
                    {
                        PreparedStatement pstat = DBOperations.connection.prepareStatement(
                            "SELECT o.orderid, m.restaurantid " +
                            "FROM orders o JOIN menuItems m ON o.menuid = m.menuid " +
                            "WHERE o.delivered = 0 LIMIT 1");
                        ResultSet rs = pstat.executeQuery();
                        if (rs.next())
                            {
                                activeOrderId = rs.getInt("orderid");
                                restaurantId = rs.getInt("restaurantid");
                            }
                        rs.close();
                        pstat.close();
                    }
                catch (SQLException e)
                    {
                        e.printStackTrace();
                    }

                if (restaurantId == -1)
                    {
                        statusLabel.setText("No active order");
                        return;
                    }

                destinationNode = restaurantToNode(restaurantId);
                // Path runs from the restaurant TO node 0 (delivery base),
                // since that's the direction the order travels.
                path = dijkstraPath(destinationNode, 0);

                if (path == null || path.isEmpty())
                    {
                        statusLabel.setText("No route to restaurant " + restaurantId);
                        return;
                    }

                totalDistance = 0;
                for (int i = 0; i < path.size() - 1; i++)
                    {
                        totalDistance += mapPoints[path.get(i)][path.get(i + 1)];
                    }

                secondsRemaining = totalDistance;
                statusLabel.setText("Delivering... " + secondsRemaining + "s remaining");

                animationStartMillis = System.currentTimeMillis();

                countdownTimer = new Timer(1000, e ->
                    {
                        secondsRemaining--;
                        if (secondsRemaining <= 0)
                            {
                                countdownTimer.stop();
                                markOrderDelivered();
                                statusLabel.setText("Order delivered!");
                            }
                        else
                            {
                                statusLabel.setText("Delivering... " + secondsRemaining + "s remaining");
                            }
                    });
                countdownTimer.start();

                // ~33fps animation — recomputes progress from elapsed real time,
                // so it stays perfectly in sync with the countdown.
                animationTimer = new Timer(30, e ->
                    {
                        double elapsedSeconds = (System.currentTimeMillis() - animationStartMillis) / 1000.0;
                        progressDistance = Math.min(elapsedSeconds, totalDistance);
                        repaint();
                        if (progressDistance >= totalDistance)
                            {
                                animationTimer.stop();
                            }
                    });
                animationTimer.start();
            }

        // Maps a restaurant id to a node index. 
        private int restaurantToNode(int restaurantId)
            {
                return restaurantId % nodeCount;
            }

        // Dijkstra's — returns the shortest-path node sequence from source to target,
        // or null if no path exists.
        private List<Integer> dijkstraPath(int source, int target)
            {
                int[] dist = new int[nodeCount];
                int[] prev = new int[nodeCount];
                boolean[] visited = new boolean[nodeCount];
                Arrays.fill(dist, Integer.MAX_VALUE);
                Arrays.fill(prev, -1);
                dist[source] = 0;

                for (int i = 0; i < nodeCount; i++)
                    {
                        int u = -1;
                        int smallest = Integer.MAX_VALUE;
                        for (int v = 0; v < nodeCount; v++)
                            {
                                if (!visited[v] && dist[v] < smallest)
                                    {
                                        smallest = dist[v];
                                        u = v;
                                    }
                            }
                        if (u == -1) break;
                        if (u == target) break;

                        visited[u] = true;

                        for (int v = 0; v < nodeCount; v++)
                            {
                                if (mapPoints[u][v] != 0 && !visited[v])
                                    {
                                        int alt = dist[u] + mapPoints[u][v];
                                        if (alt < dist[v])
                                            {
                                                dist[v] = alt;
                                                prev[v] = u;
                                            }
                                    }
                            }
                    }

                if (dist[target] == Integer.MAX_VALUE) return null;

                // Walk back from target to source via prev[]
                List<Integer> result = new ArrayList<>();
                for (int at = target; at != -1; at = prev[at])
                    {
                        result.add(at);
                    }
                Collections.reverse(result);
                return result;
            }

        // Marks the current active order as delivered in the database.
        private void markOrderDelivered()
            {
                if (activeOrderId == -1) return;
                try
                    {
                        PreparedStatement pstat = DBOperations.connection.prepareStatement(
                            "UPDATE orders SET delivered = 1 WHERE orderid = ?");
                        pstat.setInt(1, activeOrderId);
                        pstat.executeUpdate();
                        pstat.close();
                    }
                catch (SQLException e)
                    {
                        e.printStackTrace();
                    }
            }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw all edges in gray first
        g2.setStroke(new BasicStroke(2f));
        g2.setColor(Color.GRAY);
        for (int i = 0; i < nodeCount; i++) {
            for (int j = i + 1; j < nodeCount; j++) {
                if (mapPoints[i][j] != 0) {
                    g2.drawLine(nodePositions[i][0], nodePositions[i][1],
                                nodePositions[j][0], nodePositions[j][1]);
                }
            }
        }

        // Draw red progress along the path
        if (path != null && path.size() >= 2) {
            g2.setStroke(new BasicStroke(4f));
            g2.setColor(Color.RED);
            double remaining = progressDistance;

            for (int i = 0; i < path.size() - 1 && remaining > 0; i++) {
                int a = path.get(i);
                int b = path.get(i + 1);
                int edgeWeight = mapPoints[a][b];

                int ax = nodePositions[a][0], ay = nodePositions[a][1];
                int bx = nodePositions[b][0], by = nodePositions[b][1];

                if (remaining >= edgeWeight) {
                    // Whole edge traversed
                    g2.drawLine(ax, ay, bx, by);
                    remaining -= edgeWeight;
                } else {
                    // Partial edge — interpolate from a toward b
                    double frac = remaining / (double) edgeWeight;
                    int px = (int) (ax + (bx - ax) * frac);
                    int py = (int) (ay + (by - ay) * frac);
                    g2.drawLine(ax, ay, px, py);
                    remaining = 0;
                }
            }
        }

        // Draw nodes
        g2.setStroke(new BasicStroke(1f));
        int nodeSize = 30;
        for (int i = 0; i < nodeCount; i++) {
            int x = nodePositions[i][0] - nodeSize / 2;
            int y = nodePositions[i][1] - nodeSize / 2;

            if (i == 0 || i == destinationNode) {
                g2.setColor(Color.RED);
            } else {
                g2.setColor(Color.BLUE);
            }
            g2.fillOval(x, y, nodeSize, nodeSize);

            g2.setColor(Color.WHITE);
            g2.drawString("", nodePositions[i][0] - 4, nodePositions[i][1] + 5);
        }
    }
}