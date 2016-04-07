package com.importsource.fxml.core;

import java.io.File;
import java.util.Iterator;

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
public class XmlToHtml {
	public static void main(String[] args) {
		File myXML = Source.getFile("conf.xml");
		XmlToHtml x2h=new XmlToHtml();
		x2h.toHtml(myXML);
	}

	public  String toHtml(File myXML) {
		SAXReader sr = new SAXReader();
		StringBuilder sb=new StringBuilder();
		try {
			Document doc = sr.read(myXML);
			Element root = doc.getRootElement();
			
			sb.append("<h4>"+root.getName()+"</h4>");
			sdf(root, sb);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		System.out.println(sb.toString());
		return sb.toString();
	}

	private static StringBuilder sdf(Element root, StringBuilder sb) {
		sb.append("<table border=\"1\" style=\"border-collapse:collapse;\">");
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
			
			for(@SuppressWarnings("unchecked")
			Iterator<Element> childs = (Iterator<Element>) father.elementIterator();childs.hasNext();){
				sb.append("<td>");
				Element e=childs.next();
				if(e.isTextOnly()){
					sb.append(e.getText());
				}else{
					sdf(e,sb);
				}
				
				sb.append("</td>");
			}//properities里边的东西
			sb.append("");
			sb.append("</tr>");
			
			
			

			

		}
		sb.append("</table>");
		return sb;
	}
}
