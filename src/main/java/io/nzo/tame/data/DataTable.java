package io.nzo.tame.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

public class DataTable
{
	public Vector<DataRow> dataList = new Vector<>();
	public DataRow row = null;
	private Iterator<DataRow> iter = null;
	
	public DataTable()
	{
	}
	
	public void add( DataRow o )
	{
		this.dataList.add( o );
	}

	public void add( HashMap<String, Object> map )
	{
		this.dataList.add( new DataRow(map) );
	}
	
	public void remove( int index )
	{
		this.dataList.remove(index);
	}
	public void remove( Object o )
	{
		this.dataList.remove(o);
	}
	public void clear()
	{
		this.dataList.clear();
	}
	
	public DataRow get(int index)
	{
		return dataList.get(index);
	}
	
	public DataTable( Vector<Object> result)
	{
		Iterator<Object> iterator = result.iterator();
		while( iterator.hasNext() )
		{
			this.dataList.add((DataRow) iterator.next());
		}
		this.iter = this.dataList.iterator();
	}
	
	@SuppressWarnings("unchecked")
	public DataTable( ArrayList<Object> result)
	{
		Iterator<Object> iterator = result.iterator();
		while( iterator.hasNext() )
		{
			this.dataList.add( new DataRow( (HashMap<String,Object>) iterator.next() ) );
		}
		this.iter = this.dataList.iterator();
	}
	
	public void setIterator()
	{
		this.iter = this.dataList.iterator();
	}
	
	public int count()
	{
		return this.dataList.size();
	}
	
	public DataTable clone()
	{
		DataTable tempList = new DataTable();
		for( DataRow dr : dataList )
		{
			tempList.add(dr.clone());
		}
		return tempList;
	}
	
	public Iterator<DataRow> getIterator()
	{
		return this.iter;
	}
	
	public Vector<DataRow> getMap()
	{
		return this.dataList;
	}
	
	public boolean next()
	{
		if(getIterator() != null)
		{
			if(this.getIterator().hasNext())
			{
				this.row = (DataRow)this.getIterator().next();
				return true;
			}
			else
			{
				this.row = new DataRow();
				return false;
			}
		}
		else
		{
			this.row = new DataRow();
			return false;
		}
	}
	
	public boolean hasNext()
	{
		if(getIterator() != null)
		{
			return this.getIterator().hasNext();
		}
		else
		{
			return false;
		}
	}
	
	public DataRow getDataRow()
	{
		return this.row.clone();
	}

	public void setDataRow(DataRow dr )
	{
		int index;
		index = this.dataList.indexOf(this.row);
		this.row = dr;
		try
		{
			this.dataList.set(index, this.row);
		}
		catch(Exception e)
		{
		}
	}
	
}
