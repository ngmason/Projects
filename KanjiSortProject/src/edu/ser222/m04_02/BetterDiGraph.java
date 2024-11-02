package edu.ser222.m04_02;

/**
 * A new class called BetterDiGraph that implements the EditableDiGraph interface.
 *
 * Completion time: 2 hours
 *
 * @author Mason, Acuna, Buckner
 * @version 4/25/2024
 */

import java.util.HashMap;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class BetterDiGraph implements EditableDiGraph
{
	private HashMap<Integer, LinkedList<Integer>> adjacencyMap;
	private int numEdge;
	private int numVertex;
	
	public BetterDiGraph()
	{
		this.adjacencyMap = new HashMap<>();
		this.numEdge = 0;
		this.numVertex = 0;
	}
	
	@Override
	public void addEdge(int v, int w)
	{
		if(!adjacencyMap.containsKey(v))
		{
			addVertex(v);
		}
		if(!adjacencyMap.containsKey(w))
		{
			addVertex(w);
		}
		if(!adjacencyMap.get(v).contains(w))
		{
			adjacencyMap.get(v).add(w);
			numEdge++;
		}
	}
	
	@Override
	public void addVertex(int v)
	{
		if(!adjacencyMap.containsKey(v))
		{
			adjacencyMap.put(v, new LinkedList<>());
			numVertex++;
		}
	}
	
	@Override
	public Iterable<Integer> getAdj(int v)
	{
		if(!adjacencyMap.containsKey(v))
		{
			return new LinkedList<>();
		}
		return adjacencyMap.get(v);
	}
	
	@Override
	public int getEdgeCount()
	{
		return numEdge;
	}
	
	public int getIndegree(int v) throws NoSuchElementException
	{
		if(!adjacencyMap.containsKey(v))
		{
			throw new NoSuchElementException("Vertex does not exist.");
		}
		int indegree = 0;
		for(LinkedList<Integer> edges : adjacencyMap.values())
		{
			if(edges.contains(v))
			{
				indegree++;
			}
		}
		return indegree;
	}
	
	@Override
	public int getVertexCount()
	{
		return numVertex;
	}
	
	@Override
	public void removeEdge(int v, int w)
	{
		if(adjacencyMap.containsKey(v) && adjacencyMap.get(v).remove((Integer)w))
		{
			numEdge--;
		}
	}
	
	@Override
	public void removeVertex(int v)
	{
		if(adjacencyMap.containsKey(v))
		{
			adjacencyMap.remove(v);
			for(LinkedList<Integer> edges : adjacencyMap.values())
			{
				edges.remove((Integer)v);
			}
			numVertex--;
		}
	}
	
	@Override
	public Iterable<Integer> vertices()
	{
		return adjacencyMap.keySet();
	}
	
	
	@Override
	public boolean isEmpty()
	{
		return numVertex == 0;
	}
	
	@Override
	public boolean containsVertex(int v)
	{
		return adjacencyMap.containsKey(v);
	}
}