package com.importsource.fxml.core;

import java.io.File;
import java.util.Iterator;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.importsource.conf.Source;

/**
 * 把xml转换成html
 * @author Hezf
 *
 */
public class Xml2Div implements Xml2Html {
	private  String backgroundColor;
	private  String color;
	
	private  int c_r=0;
	private  int c_g=0;
	private  int c_b=0;
	
	private  int b_r=255;
	private  int b_g=255;
	private  int b_b=255;

	public static void main(String[] args) {
		File myXML = Source.getFile("conf.xml");
		Xml2Html x2h=new Xml2Div();
		String div=x2h.toHtml(myXML);
		System.out.println(div);
	}

	/**
	 * xml转html
	 * @param myXML 你要转换的xml文件
	 * @return String html 字符串
	 */
	public  String toHtml(File myXML) {
		SAXReader sr = new SAXReader();
		StringBuilder sb=new StringBuilder();
		try {
			Document doc = sr.read(myXML);
			Element root = doc.getRootElement();
			
			sb.append("<h4><i>"+root.getName()+"</i></h4>");
			append(root, sb);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	

	private  StringBuilder append(Element root, StringBuilder sb) {
		newColor();
		sb.append("<div  style=\"color:"+color+";backgroud-color:"+backgroundColor+";border:1px solid #000;border-collapse:collapse;margin:10px;\">");
		for (@SuppressWarnings("unchecked")
		Iterator<Element> fathers = (Iterator<Element>) root.elementIterator(); fathers.hasNext();) {
		    //从根目录开始建立table
			//<table>
			//   <tr>
			//       <td></td>
			//       <td></td>
			//   </tr>
			//    <tr>
			//       <td></td>
			//       <td></td>
			//    </tr>
			// </table>
			
			//搞第一行
			Element father = (Element) fathers.next();//properties
			sb.append("<div style=\"border:1px solid #000;\">");
			sb.append("<div style=\"font-weight:bold;\">");
			appendAttribute(sb, father);
			sb.append("</div>");
			
			for(@SuppressWarnings("unchecked")
			Iterator<Element> childs = (Iterator<Element>) father.elementIterator();childs.hasNext();){
				sb.append("<div style=\"padding:5px;\">");
				Element e=childs.next();
				String nodeName=e.getName();
				sb.append("<i >"+nodeName+"</i>");
				appendAttribute(sb, e);
				if(e.isTextOnly()){
					sb.append(e.getText());
				}else{
					append(e,sb);
				}
				
				sb.append("</div>");
			}//properities里边的东西
			sb.append("");
			sb.append("</div>");
			
			
			

			

		}
		sb.append("</div>");
		return sb;
	}

	private void appendAttribute(StringBuilder sb, Element e) {
		Iterator<Attribute> attributes=e.attributeIterator();
		sb.append("<div style=\"padding:5px;border:1px solid #efefef;margin:5px;\">");
		while(attributes.hasNext()){
			Attribute attribute=attributes.next();
			String name=attribute.getQualifiedName();
			String value=attribute.getValue();
			sb.append("<p>");
			sb.append(name);
			sb.append(":");
			sb.append(value);
			sb.append("</p>");
		}
		sb.append("</div>");
	}

	private  void newColor() {
		color=getColorHex();
		backgroundColor=getBackgroundColoeHex();
		
	}

	private  String getBackgroundColoeHex() {
		//OperaColor operaColor=new OperaColor(r, g, b)
		b_r=b_r-1;
		b_g=b_g-1;
		b_b=b_b-1;
		return b_r+" "+b_g+" "+b_b;
	}

	private  String getColorHex() {
		c_r=c_r+1;
		c_g=c_g+1;
		c_b=c_b+1;
		return c_r+" "+c_g+" "+c_b;
	}
}
