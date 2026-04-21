//Created by: Joseph Lalor
//Student Number: c00312883
//Date: 20/04/2026
//Purpose: Tracking panel for order

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class TrackingPanel extends JPanel 
    {
        private int[][] mapPoints = new int[19][19];
        private int nodeCount = 18;
        private int[][] nodePositions;
        private int[][] edges = 
            {
                {0, 1, 4},  // node 0 -> node 1, weight 4
                {1, 2, 3},
                {1, 15, 4},
                {2, 12, 8},
                {2, 3, 3},
                {3, 4, 2},
                {4, 6, 5},
                {6, 5, 12},
                {6, 7, 11},
                {6, 8, 9},
                {8, 11, 7},
                {10, 11, 10},
                {8, 9, 8},
                {12, 13, 3},
                {13, 14, 2},
                {15, 16, 5},
                {16, 17, 7},
                {16, 18, 6}
            };



        public TrackingPanel()
            {
                for (int[] edge : edges) 
                    {
                        mapPoints[edge[0]][edge[1]] = edge[2];
                        mapPoints[edge[1]][edge[0]] = edge[2];
                    }
                this.mapPoints = mapPoints;
                this.nodeCount = nodeCount;
                this.nodePositions = new int[nodeCount][2];
                
                // Arrange nodes in a circle
                int centerX = 300, centerY = 300, radius = 200;
                for (int i = 0; i < nodeCount; i++) {
                    double angle = 2 * Math.PI * i / nodeCount;
                    nodePositions[i][0] = (int) (centerX + radius * Math.cos(angle));
                    nodePositions[i][1] = (int) (centerY + radius * Math.sin(angle));
                }
            }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw edges
        g2.setColor(Color.GRAY);
        for (int i = 0; i < nodeCount; i++) {
            for (int j = i + 1; j < nodeCount; j++) {
                if (mapPoints[i][j] != 0) {
                    g2.drawLine(nodePositions[i][0], nodePositions[i][1],
                                nodePositions[j][0], nodePositions[j][1]);
                }
            }
        }

        // Draw nodes
        int nodeSize = 30;
        for (int i = 0; i < nodeCount; i++) {
            int x = nodePositions[i][0] - nodeSize / 2;
            int y = nodePositions[i][1] - nodeSize / 2;

            g2.setColor(Color.BLUE);
            g2.fillOval(x, y, nodeSize, nodeSize);

            g2.setColor(Color.WHITE);
            g2.drawString(String.valueOf(i), nodePositions[i][0] - 4, nodePositions[i][1] + 5);
        }
    }
}