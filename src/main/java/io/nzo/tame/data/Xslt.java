package io.nzo.tame.data;

//import java.io.BufferedReader;
import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.PrintWriter;
//import java.io.StringReader;
//import java.io.StringWriter;
//import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
//import java.util.HashMap;

//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.xml.transform.OutputKeys;
//import javax.xml.transform.Transformer;
//import javax.xml.transform.TransformerConfigurationException;
//import javax.xml.transform.TransformerException;
//import javax.xml.transform.TransformerFactory;
//import javax.xml.transform.TransformerFactoryConfigurationError;
//import javax.xml.transform.dom.DOMSource;
//import javax.xml.transform.stream.StreamResult;

//import oracle.xml.parser.v2.DOMParser;
//import oracle.xml.parser.v2.XMLDocument;
//import oracle.xml.parser.v2.XMLParseException;
//import oracle.xml.parser.v2.XSLException;
//import oracle.xml.parser.v2.XSLProcessor;
//import oracle.xml.parser.v2.XSLStylesheet;

import org.slf4j.LoggerFactory;
//import org.xml.sax.InputSource;
//import org.xml.sax.SAXException;
//import com.intivsoft.module.common.Convert;

/**
 * XML / XSL Transformation II
 * 
 * @author		Jung Seong Hun (gemzone@intivsoft.com)
 * @version		2.0
 */
public class Xslt
{
	public static final org.slf4j.Logger logger = LoggerFactory.getLogger(Xslt.class);

	
	boolean useLog	= false;
	
	Object	xml		=	null;
	Object 	xsl		=	null;
	String	cookie	=	"";
	String 	baseURL = 	"";
	String  template = "";
	String  firstChildAppendXml = "";
	
	HttpServletRequest request = null;
	@Deprecated
	public Xslt()
	{
		
	}
	
	@Deprecated
	public Xslt(HttpServletRequest request)
	{
		this.request = request;
	}
	
	public void setLog()
	{
		useLog		= true;
	}
	
	public Xslt(HttpServletRequest request, String urlContext, String inputXml, String inputXsl, String paramData, String callTemplateName, String appendXml)
	{
		init(request, urlContext, inputXml, inputXsl, paramData, callTemplateName, appendXml);
	}
	
	public Xslt(HttpServletRequest request, String urlContext, String inputXml, String inputXsl, String paramData, String callTemplateName)
	{
		init(request, urlContext, inputXml, inputXsl, paramData, callTemplateName, "");
	}
	
	private void init(HttpServletRequest request, String urlContext, String inputXml, String inputXsl, String paramData, String callTemplateName, String appendXml) 
	{
		try 
		{
			if( appendXml != null && !"".equals(appendXml) ) 
			{
				this.firstChildAppendXml = appendXml;
			} 
			else 
			{
				this.firstChildAppendXml = "";
			}
			
			// URL/String Check
			if( !"".equals( urlContext ) && urlContext != null &&
				!"".equals( inputXml ) 
				&& !"".equals( inputXsl ) 
				&& inputXml.toString().length() >= 1 
				&& inputXsl.toString().length() >= 1 )
			{
				// HttpServletRequest
				this.request = request;
				
				String requestLog = "";
				
				if( this.request != null ) 
				{
					// Cookie
					Cookie[] cookies = this.request.getCookies();
					if(cookies != null)
					{
						String cookieData = "";
						for (int i = 0; i < cookies.length; ++i)
						{
							cookieData += cookies[i].getName() + "=" + cookies[i].getValue() + "; ";
						}
						this.cookie = cookieData;
					}
				}
				this.baseURL = urlContext ;
				
				// XML
				if( ".".equals(inputXml.substring( 0, 1 )) || "/".equals(inputXml.substring( 0, 1 )) || !"<".equals(inputXml.substring( 0, 1 )) ) 
				{
					inputXml = inputXml.replace("../", "").replace("./", "").replaceFirst("/", "");
					if( "".equals(paramData))
					{
						this.xml = new URL(baseURL + "/" + inputXml);
						requestLog += inputXml + " ";
					} 
					else
					{
						this.xml = new URL(baseURL + inputXml + "?" + paramData);
					}
				}
				else
				{
					this.xml = (String)inputXml;
				}
				
				// XSL 
				if( ".".equals( inputXsl.substring( 0, 1 )  ) || "/".equals(inputXsl.substring( 0, 1 )) || !"<".equals(inputXsl.substring( 0, 1 )) ) 
				{
					inputXsl = inputXsl.replace("../", "").replace("./", "").replaceFirst("/", "");
					this.xsl = new URL(baseURL + "/" + inputXsl);
					requestLog += inputXsl;
				}
				else
				{
					this.xsl = (String)inputXsl;
				}
				logger.debug( " Xslt " + requestLog +" " +  callTemplateName );
				
				// call Template
				this.template = callTemplateName;
			}
			else
			{
				logger.debug("xslt parser error");
			}
		}
		catch (MalformedURLException e) 
		{
			e.printStackTrace();
		}
	}

	@Deprecated
	public Xslt(String xmlDoc, String xslURL) throws MalformedURLException
	{
		xml = xmlDoc;
		xsl = new URL(xslURL);
	}
	
	@Deprecated
	public Xslt(URL xmlURL, String xslURL) throws MalformedURLException
	{
		xml = xmlURL;
		xsl = new URL(xslURL);
	}
	
	@Deprecated
	public void setCallTemplate(String templateName)
	{
		template = templateName;
	}
	
	/*
	
	public String createXml(String xsl,  String addXml)
	{
		try
		{
			oracle.xml.parser.v2.XMLDocument xml = new oracle.xml.parser.v2.XMLDocument();
			HashMap<String, String> map = new HashMap<>();
			map.put("type", "test/xsl");
			map.put("href", xsl);
			org.jdom.ProcessingInstruction p = new org.jdom.ProcessingInstruction("xml-stylesheet", map);
			xml.insertBefore(xml.createProcessingInstruction(p.getTarget(), p.getData()), null);
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			StringWriter stringWriter = new StringWriter(); 
			StreamResult streamResult = new StreamResult(stringWriter); 
			DOMSource source = new DOMSource(xml);
			try 
			{
				transformer.transform(source, streamResult);
			} 
			catch (TransformerException e1) 
			{
				e1.printStackTrace();
				return "TransformerException";
			}
		
			return stringWriter.toString() + addXml;
		}
		catch (TransformerConfigurationException e1) 
		{
			e1.printStackTrace();
			return "TransformerConfigurationException";
		} 
		catch (TransformerFactoryConfigurationError e1) 
		{
			e1.printStackTrace();
			return "TransformerFactoryConfigurationError";
		}
	}
	*/
	@Deprecated
	public void setXmlURL(String url) throws MalformedURLException
	{
		Cookie[] cookies = this.request.getCookies();
		
		if(cookies != null)
		{
			String cookieData = "";
			
			for (int i = 0; i < cookies.length; ++i)
			{
				cookieData += cookies[i].getName() + "=" + cookies[i].getValue() + "; ";
			}
			cookie = cookieData;
		}
		
		URL xmlURL = new URL(url);
		xml = xmlURL;
	}
	@Deprecated
	public void setXmlURL(String url, String _cookie) throws IOException
	{
		URL xmlURL = new URL(url);
		
		cookie = _cookie;

		xml 	= xmlURL;
	}
	@Deprecated
	public void setXmlURL(String url, Cookie[] _cookie) throws IOException
	{
		URL xmlURL = new URL(url);

		if(_cookie != null)
		{
			String cookieData = "";
			
			for (int i = 0; i < _cookie.length; ++i)
			{
				cookieData += _cookie[i].getName() + "=" + _cookie[i].getValue() + "; ";
			}
			cookie = cookieData;
		}
		
		xml 	= xmlURL;
	}
	@Deprecated
	public void setXslURL(String url) throws MalformedURLException
	{
		URL xslURL = new URL(url);
		xsl = xslURL;
	}
	@Deprecated
	public void setXmlString(String xmlString)
	{
		xml = xmlString;
	}
	@Deprecated
	public void setXslString(String xslString)
	{
		xsl = xslString;
	}
	
	@Deprecated
	public void setBaseURL(String url)
	{
		baseURL = url +"/";
	}
/*
	public String execute()
	{
		//String encoding = new sun.misc.BASE64Encoder().encode("intiv:dlsxlqm0935".getBytes());
		
		String xslt = "";
		boolean processorFail = false;
		boolean authMode = false;
		try
		{
			XSLProcessor processor = new XSLProcessor();
			processor.showWarnings(false);
			processor.setBaseURL( new URL(baseURL + "/" ) );	// TODO: template경로를 찾을수없을때 baseURL 의 문제일가능성이 높음
			processor.setErrorStream(System.err);
			processor.setOutputEncoding("utf-8");
	
			XSLStylesheet xslSheet = null;
			// XSL
			if(useLog)
			{
				logger.debug( " XSL " + xsl.toString());
			}
						
			String xslSource = "";
			
			if( xsl instanceof java.net.URL )
			{
				// URLConnection connection = ((URL)xsl).openConnection();
				HttpURLConnection connection = (HttpURLConnection)((URL)xsl).openConnection();
				connection.setRequestProperty("Cookie", cookie);
				if( request != null && "BASIC".equals( request.getAuthType() ) )
				{
					authMode = true;
					connection.setRequestProperty("Authorization", request.getHeader("Authorization"));
				}
				//connection.setRequestProperty("Authorization", "Basic " + encoding);
				//connection.connect();
				BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8" ) );
				String outputLine = "";
				String tmp = "";
				while((tmp = br.readLine()) != null)
				{
					outputLine += tmp;
				}
				br.close();
				xslSource = outputLine;
			}
			else if( xsl instanceof java.lang.String )
			{
				xslSource = (String)xsl;
			}
			else
			{
				xslSource = new Convert(xsl).toString();
			}
			
			if( !"".equals(this.template))
			{
				StringBuilder sb = new StringBuilder((String)xslSource);
				Integer xslLastIndex = sb.lastIndexOf("</xsl:stylesheet>", sb.length() );
				Integer insertLastIndex = sb.lastIndexOf( "</xsl:template>", xslLastIndex) ;
				String tag = "<xsl:call-template version=\"1.0\" xmlns=\"http://www.w3.org/1999/XSL/Transform\" name=\"" + this.template + "\"></xsl:call-template>";
				sb.insert( insertLastIndex, tag.toCharArray() );
				xslSource = sb.toString();
			}
			
			if( !"".equals( this.firstChildAppendXml ) ) 
			{
				StringBuilder sb = new StringBuilder((String)xslSource);
				Integer xslLastIndex = sb.lastIndexOf("<xsl:output", sb.length() );
				sb.insert( xslLastIndex , this.firstChildAppendXml );
				xslSource = sb.toString();
			}
			
			//logger.debug(xslSource);
			//logger.debug("\n\n\n\n");
			// <xsl:variable name="test" select="document('http://intiv:dlsxlqm0935@./template.xsl')"/><xsl:copy-of select="$test"/>
			//xslSource = xslSource.replaceAll("xsl:include href=\"", "xsl:include href=\"http://intiv:dlsxlqm0935@");
			String checkXslSource = xslSource;
			{
				checkXslSource = checkXslSource.replaceAll("<!--(.*?)-->", ""); 	// 주석제거
				if( checkXslSource.indexOf("xsl:include") > -1 && authMode ) 		// 2014-09-03 버그 인증모드일때 include를 해석못함
				{
					processorFail = true;
				}
				// 해석하려면
				// <xsl:variable name="test" select="document('http://id:password@./template.xsl')"/>  해당 basic auth 관련내용필요
				// <xsl:copy-of select="$test"/>
			}
			
			if( processorFail == false )
			{
				xslSheet = processor.newXSLStylesheet( new StringReader(xslSource) );
				
				DOMParser parser = new DOMParser();
				parser.setPreserveWhitespace(true);
				
				// XML
				if(useLog)
				{
					logger.debug(  " XML " + xml.toString());
				}
				
				if( xml instanceof java.net.URL )
				{
					// URLConnection connection = ((URL)xml).openConnection();
					HttpURLConnection connection = (HttpURLConnection)((URL)xml).openConnection();
					connection.setRequestProperty("Cookie", cookie);
					if( request != null && "BASIC".equals( request.getAuthType() ) )
					{
						connection.setRequestProperty("Authorization", request.getHeader("Authorization"));
					}
					//connection.setRequestProperty("Authorization", "Basic " + encoding);
					//connection.connect();
					parser.parse(new InputStreamReader(connection.getInputStream(), "utf-8"));
				}
				else if( xml instanceof java.lang.String )
				{
					parser.parse( new InputSource(new StringReader((String)xml)) );
				}
				else
				{
					parser.parse((URL)xml);
				}
				XMLDocument xml = parser.getDocument();
				StringWriter strWriter = new StringWriter();
				processor.processXSL(xslSheet, xml, new PrintWriter(strWriter));
				xslt = strWriter.getBuffer().toString();
				strWriter.close();
			} 
			else 
			{
				xslt = "";
			}
		}
		catch (MalformedURLException e) 
		{
			e.printStackTrace();
		} 
		catch (XSLException e) 
		{
			e.printStackTrace();
		} 
		catch (XMLParseException e) 
		{
			e.printStackTrace();
		} 
		catch (SAXException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return xslt;
	}
*/
}
