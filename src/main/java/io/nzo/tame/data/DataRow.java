package io.nzo.tame.data;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataRow
{
	public HashMap<String, Object> map = new HashMap<>();
	
	public DataRow()
	{
		
	}
	
	public DataRow(JSONObject json)
	{
		HashMap<String, Object> map = new HashMap<>();
		Iterator<?> iter = json.keys();
		while(iter.hasNext() )
		{
			String key = (String)iter.next();
			if( json.has(key) && !map.containsKey(key) )
			{
				try 
				{
					map.put(key, json.get(key));
				} 
				catch (JSONException e) 
				{
					e.printStackTrace();
				}
			}
		}
		this.map = map;
	}
	
//	public <T> T toClass( Class<T> clazz )
//	{
//		ObjectMapper mapper = new ObjectMapper();
//		String json = toJSONObject().toString();
//		T t = null;
//		try {
//			t = mapper.readValue(json, clazz);
//		} catch (JsonParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (JsonMappingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		return t;
//	}
	
	public JSONObject toJSONObject()
	{
		return new JSONObject(map);
	}
	
	public DataRow(HashMap<String, Object> map)
	{
		this.map = map;
	}

	public void set(String key, Object value)
	{
		this.map.put(key, value);
	}
	
	public void setMerge( HashMap<String, Object> mergeMap )
	{
		if( mergeMap != null )
		{
	        for( String key : mergeMap.keySet() )	// d의 내용을 반복자
	        {
	            // key
	        	this.set(key, mergeMap.get(key));
	        }
		}
	}
	
	public DataRow clone()
	{
		return new DataRow ( this.map );
	}
	
	public HashMap<String, Object> get()
	{
		return this.map;
	}
	
	public void clear()
	{
		this.map.clear();
	}
	
	
	public double getDouble(String columnName)
	{
		Object tmpData = this.map.get(columnName);
		double tmpValue = (double)0.0;
		try
		{
			if( tmpData == null)
			{
				return tmpValue;
			}
			else
			{
				return Double.parseDouble( tmpData.toString() );
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return tmpValue;
		}
	}
	
	public float getFloat(String columnName)
	{
		Object tmpData = this.map.get(columnName);
		float tmpValue = (float)0.0;
		try
		{
			if( tmpData == null)
			{
				return tmpValue;
			}
			else
			{
				return Float.parseFloat( tmpData.toString() );
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return tmpValue;
		}
	}
	
	public long getLong(String columnName)
	{
		Object tmpData = this.map.get(columnName);
		long tmpValue = 0;
		try
		{
			if( tmpData == null)
			{
				return tmpValue;
			}
			else if( tmpData instanceof Double)		// 2013-08-20 추가된 코드 For input string: "false"
			{
				return (long)Double.parseDouble(tmpData.toString());
			}
			else if( tmpData instanceof Float)		// 2013-08-20 추가된 코드 For input string: "false"
			{
				return (long)Float.parseFloat(tmpData.toString());
			}
			else
			{
				return Long.parseLong( tmpData.toString() );
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return tmpValue;
		}
	}
	
	public Object getObject(String columnName)
	{
		return this.map.get(columnName);
	}
	
	public boolean getBoolean(String columnName)
	{
		Object tmpData = this.map.get(columnName);
		try
		{
			if( tmpData == null)
			{
				return false;
			}
			else if( tmpData instanceof Integer)		// 2013-08-20 추가된 코드
			{
				if( Integer.parseInt(tmpData.toString()) == 1 )
				{
					return true;
				}
				else
				{
					return false;
				}
			}
			else
			{
				return Boolean.parseBoolean(tmpData.toString());
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	public int getInt(String columnName)
	{
		Object tmpData = this.map.get(columnName);
	
		try
		{
			if( tmpData == null)
			{
				return 0;
			}
			else if( tmpData instanceof Double)		// 2013-08-20 추가된 코드 For input string: "false"
			{
				return (int)Double.parseDouble(tmpData.toString());
			}
			else if( tmpData instanceof Float)		// 2013-08-20 추가된 코드 For input string: "false"
			{
				return (int)Float.parseFloat(tmpData.toString());
			}
			else if( tmpData instanceof Boolean)		// 2013-08-20 추가된 코드 For input string: "false"
			{
				if( Boolean.parseBoolean(tmpData.toString()) == true )
				{
					return 1;
				}
				else
				{
					return 0;					
				}
			}
			else if( tmpData instanceof String)		// 2013-08-20 추가된 코드 For input string: "false"
			{
				return (int)Float.parseFloat(tmpData.toString());
			}
			else
			{
				return Integer.parseInt( tmpData.toString() );
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return 0;
		}
	}
	
	public byte getByte(String columnName)
	{
		Object tmpData = this.map.get(columnName);
		
		try
		{
			if( tmpData == null)
			{
				return 0;
			}
			else if( tmpData instanceof Double)		// 2013-08-20 추가된 코드 For input string: "false"
			{
				return (byte)Double.parseDouble(tmpData.toString());
			}
			else if( tmpData instanceof Float)		// 2013-08-20 추가된 코드 For input string: "false"
			{
				return (byte)Float.parseFloat(tmpData.toString());
			}
			else if( tmpData instanceof Boolean)		// 2013-08-20 추가된 코드 For input string: "false"
			{
				if( Boolean.parseBoolean(tmpData.toString()) == true )
				{
					return 1;
				}
				else
				{
					return 0;					
				}
			}
			else if( tmpData instanceof String)		// 2013-08-20 추가된 코드 For input string: "false"
			{
				return (byte)Float.parseFloat(tmpData.toString());
			}
			else
			{
				return Byte.parseByte( tmpData.toString() );
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return 0;
		}
	}
	
	public short getShort(String columnName)
	{
		Object tmpData = this.map.get(columnName);
		
		try
		{
			if( tmpData == null)
			{
				return 0;
			}
			else if( tmpData instanceof Double)		// 2013-08-20 추가된 코드 For input string: "false"
			{
				return (short)Double.parseDouble(tmpData.toString());
			}
			else if( tmpData instanceof Float)		// 2013-08-20 추가된 코드 For input string: "false"
			{
				return (short)Float.parseFloat(tmpData.toString());
			}
			else if( tmpData instanceof Boolean)		// 2013-08-20 추가된 코드 For input string: "false"
			{
				if( Boolean.parseBoolean(tmpData.toString()) == true )
				{
					return 1;
				}
				else
				{
					return 0;					
				}
			}
			else if( tmpData instanceof String)		// 2013-08-20 추가된 코드 For input string: "false"
			{
				return (short)Float.parseFloat(tmpData.toString());
			}
			else
			{
				return Short.parseShort( tmpData.toString() );
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return 0;
		}
	}
	
	
	
	public String getString(String columnName)
	{
		Object tmpData = this.map.get(columnName);
		try
		{
			if( tmpData == null)
			{
				return "";
			}
			else
			{
				return tmpData.toString();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "";
		}
	}
	
	public boolean isNull(String columnName)
	{
		if( this.map.get(columnName) == null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	
}
