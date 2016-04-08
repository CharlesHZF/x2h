package com.importsource.fxml.core;

import java.io.File;
import java.util.Calendar;
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
public class Xml2Table implements Xml2Html{
	private  String backgroundColor="#fff";
	private  String color="#000";
	
	private  int c_r=255;
	private  int c_g=255;
	private  int c_b=255;
	
	private  int b_r=0;
	private  int b_g=0;
	private  int b_b=0;

	public static void main(String[] args) {
		File myXML = Source.getFile("conf.xml");
		Xml2Html x2h=new Xml2Table();
		String table=x2h.toHtml(myXML);
		System.out.println(table);
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
		sb.append("<table border=\"1\" style=\"color:"+color+";background-color:"+backgroundColor+";border-collapse:collapse;margin:10px;\">");
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
			
			sb.append("<tr>");
			sb.append("<td style=\"font-weight:bold;\">");
			appendAttribute(sb, father);
			sb.append("</td>");
			
			for(@SuppressWarnings("unchecked")
			Iterator<Element> childs = (Iterator<Element>) father.elementIterator();childs.hasNext();){
				sb.append("<td style=\"padding:5px;\">");
				Element e=childs.next();
				String nodeName=e.getName();
				sb.append("<i >"+nodeName+"</i><br/>");
				appendAttribute(sb, e);
				if(e.isTextOnly()){
					sb.append(e.getText());
				}else{
					
					append(e,sb);
				}
				sb.append("</td>");
			}//properities里边的东西
			sb.append("");
			sb.append("</tr>");
			
			
			

			

		}
		sb.append("</table>");
		return sb;
	}

	private void clearColor() {
		c_r=255;
		c_g=255;
		c_b=255;
		
		b_r=0;
		b_g=0;
		b_b=0;
		
	}

	private void appendAttribute(StringBuilder sb, Element e) {
		Iterator<Attribute> attributes=e.attributeIterator();
		boolean hasNext = e.attributeIterator().hasNext();
		if(hasNext){
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
	}

	private  void newColor() {
		color=getColorHex();
		backgroundColor=getBackgroundColoeHex();
		
	}

	private  String getBackgroundColoeHex() {
		//OperaColor operaColor=new OperaColor(r, g, b)
		b_r=b_r+10;
		b_g=b_g+10;
		b_b=b_b+10;
		return new OperaColor(b_r,b_g,b_b).toHex(b_r,b_g,b_b);
	}

	private  String getColorHex() {
		c_r=c_r-10;
		c_g=c_g-10;
		c_b=c_b-10;
		return new OperaColor(c_r,c_g,c_b).toHex(c_r,c_g,c_b);
	}
}
