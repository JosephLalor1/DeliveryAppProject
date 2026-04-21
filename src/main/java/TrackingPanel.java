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
                
                // Arrange nodes in a grid
                int cols = (int) Math.ceil(Math.sqrt(nodeCount));
                int rows = (int) Math.ceil((double) nodeCount / cols); 
                int marginX = 75, marginY = 75;
                int spacingX = 120, spacingY = 120;
                for (int i = 0; i < nodeCount; i++)
                    {
                        int row = i / cols;
                        int col = i % cols;
                        nodePositions[i][0] = marginX + col * spacingX;
                        nodePositions[i][1] = marginY + row * spacingY;
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
            g2.drawString("", nodePositions[i][0] - 4, nodePositions[i][1] + 5);
        }
    }
}