package tanquery;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.jespxml.*;
import org.jespxml.excepciones.TagHijoNotFoundException;
import org.jespxml.modelo.Tag;
import org.xml.sax.SAXException;
@SuppressWarnings("unused")
public class XmlConfig {
	
	
/*
	@SuppressWarnings("deprecation")
	public static void saveXml(Configuration X) {
		// TODO Auto-generated method stub
		Tag raiz,servidor, puerto, usuario, contra,rutalog;
		raiz=new Tag("Tanqery");
		
		
		servidor=new Tag("mysqlserver");
		puerto=new Tag("port");
		usuario=new Tag("user");
		contra=new Tag("password");
		rutalog=new Tag("logpath");
		
		servidor.addContenido(X.server.getText());
		puerto.addContenido(X.port.getText());
		usuario.addContenido(X.user.getText());
		contra.addContenido(X.password.getText());
		rutalog.addContenido(X.path.getText());
		servidor.addContenido("192.168.1.1");
		puerto.addContenido("3333");
		usuario.addContenido("root");
		contra.addContenido("toor");
		rutalog.addContenido("/4x/Desktop");
		
		raiz.addTagHijo(servidor);
		raiz.addTagHijo(puerto);
		raiz.addTagHijo(usuario);
		raiz.addTagHijo(contra);
		raiz.addTagHijo(rutalog);
		
		JespXML xml=new JespXML(new File("configuracion.xml"));
		try {
			xml.escribirXML(raiz);
		} catch (FileNotFoundException | ParserConfigurationException
				| TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	public static void load(Configuration X) throws TagHijoNotFoundException {
		JespXML xmlfile=new JespXML(new File("configuracion.xml"));
		//String servidor = null, puerto, usuario, password, rutalog;
		try {
			Tag raiz=xmlfile.leerXML();
			for(Tag hijo:raiz.getTagsHijos()){
				System.out.println("Hijo de raiz:"+hijo.getContenido());
			}
			X.setServer(raiz.getTagHijoByName("mysqlserver").getContenido());
			X.setPort(raiz.getTagHijoByName("port").getContenido());
			X.setUser(raiz.getTagHijoByName("user").getContenido());
			X.setPassword(raiz.getTagHijoByName("password").getContenido());
			X.setPath(raiz.getTagHijoByName("logpath").getContenido());
			//X.server.setText(servidor);
			
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("Servidor=" + servidor);
		
	}
	
	@SuppressWarnings("deprecation")
	public static ArrayList<String> load() throws TagHijoNotFoundException {
		ArrayList<String> xmlDatos=null;
		JespXML xmlfile=new JespXML(new File("configuracion.xml"));
		//String servidor = null, puerto, usuario, password, rutalog;
		try {
			xmlDatos=new ArrayList<String>();
			Tag raiz=xmlfile.leerXML();
			//for(Tag hijo:raiz.getTagsHijos()){
				//System.out.println("Hijo de raiz:"+hijo.getContenido());
			//}
			xmlDatos.add(0,raiz.getTagHijoByName("mysqlserver").getContenido());
			xmlDatos.add(1,raiz.getTagHijoByName("port").getContenido());
			xmlDatos.add(2,raiz.getTagHijoByName("user").getContenido());
			xmlDatos.add(3,raiz.getTagHijoByName("password").getContenido());
			xmlDatos.add(4,raiz.getTagHijoByName("logpath").getContenido());
			//X.server.setText(servidor);
			
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("Servidor=" + servidor);
		return xmlDatos;
		
	}*/
	
}
