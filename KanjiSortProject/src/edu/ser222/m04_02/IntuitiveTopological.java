package edu.ser222.m04_02;
/**
 * A new class called IntuitiveTopological that implements the TopologicalSort interface.
 *
 * Completion time: 2 hours
 *
 * @author Mason, Acuna, Buckner
 * @version 4/26/2024
 */

import java.util.HashMap;
import java.util.LinkedList;

public class IntuitiveTopological implements TopologicalSort {
	
	private BetterDiGraph graph;
	private LinkedList<Integer> order;
	
	public IntuitiveTopological(EditableDiGraph graph) {
        this.graph = (BetterDiGraph) graph;
        order = new LinkedList<>();
        topologicalSort();
    }

    private void topologicalSort() {
        LinkedList<Integer> queue = new LinkedList<>();
        HashMap<Integer, Integer> inDegree = new HashMap<>();

        for (int vertex : graph.vertices()) {
            int degree = graph.getIndegree(vertex);
            inDegree.put(vertex, degree);
            if (degree == 0) {
                queue.add(vertex);
            }
        }

        while (!queue.isEmpty()) {
            int current = queue.poll();
            order.add(current);

            for (int neighbor : graph.getAdj(current)) {
                int degree = inDegree.get(neighbor) - 1;
                inDegree.put(neighbor, degree);

                if (degree == 0) {
                    queue.add(neighbor);
                }
            }
        }
    }
	
	@Override
    public Iterable<Integer> order()
	{
        return order;
    }

    @Override
    public boolean isDAG() 
    {
        return order.size() == graph.getVertexCount();
    }
	
	
}

