package io.nzo.tame.data;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 
 * @author 	Jung
 * @version	1.0
 *
 */
public class DataMap
{
	private LinkedHashMap<Object, DataRow> dataList = new LinkedHashMap<>();
	public DataRow row = null;	// current RowData
	private Object key = null;		// current key
	private Iterator<Object> iter = null;
	
	public DataMap()
	{
	}
	
	public void put(String s, DataRow o)
	{
		this.dataList.put(s, o);
	}
	
	public void put(Long l, DataRow o)
	{
		this.dataList.put(l, o);
	}
	
	public void put(Integer i, DataRow o)
	{
		this.dataList.put(i.longValue(), o);
	}
	
	public DataMap( Map<Integer, Object> result)
	{
		Iterator<Integer> iterator = result.keySet().iterator();
		while( iterator.hasNext() )
		{
			Long key = iterator.next().longValue();
			this.dataList.put(key, (DataRow)result.get(key));
		}
		this.iter = this.dataList.keySet().iterator();
	}
	
	public DataMap( LinkedHashMap<Long, Object> result)
	{
		Iterator<Long> iterator = result.keySet().iterator();
		while( iterator.hasNext() )
		{
			Long key = iterator.next();
			this.dataList.put(key, (DataRow)result.get(key));
		}
		this.iter = this.dataList.keySet().iterator();
	}
	
	public void clear()
	{
		this.dataList.clear();
	}
	
	public void setIterator()
	{
		this.iter = this.dataList.keySet().iterator();
	}
	
	public int count()
	{
		return this.dataList.size();
	}
	
	public Iterator<Object> getIterator()
	{
		return this.iter;
	}
	
	public LinkedHashMap<Object , DataRow> getMap()
	{
		return this.dataList;
	}
	
	public boolean next()
	{
		if(getIterator() != null)
		{
			if(this.getIterator().hasNext())
			{
				Object key = this.getIterator().next();
				this.key = key;		// current Key
				if( key instanceof Integer || key instanceof Long )
				{
					this.row = this.dataList.get(  Long.parseLong( key.toString() ) );
				}
				else 
				{
					this.row = this.dataList.get( key.toString() );
				}
				return true;
			}
			else
			{
				this.row = new DataRow();
				this.key = null;
				return false;
			}
		}
		else
		{
			this.row = new DataRow();
			this.key = null;
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
	
	public void setDataRow(DataRow dr )
	{
		this.row = dr;
		try
		{
			this.dataList.put(this.key, this.row );
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public Object getKey()
	{
		return this.key;
	}
	
	public DataRow get(Object key)
	{
		if( key instanceof Integer || key instanceof Long )
		{
			return this.dataList.get(  Long.parseLong( key.toString() ) );
		}
		else 
		{
			return this.dataList.get( key.toString() );
		}
	}
	
	public DataRow getDataRow()
	{
		return this.row.clone();
	}
}
