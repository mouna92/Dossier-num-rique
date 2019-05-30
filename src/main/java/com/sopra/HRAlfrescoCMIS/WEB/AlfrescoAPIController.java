package com.sopra.HRAlfrescoCMIS.WEB;



import com.couchbase.client.deps.io.netty.handler.codec.http.HttpRequest;
import com.sopra.HRAlfrescoCMISAPI.CmisSessionSingleton;
import com.sopra.HRAlfrescoCMISAPI.DownloadTest;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.commons.impl.Base64.OutputStream;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;
import java.io.File;
import java.io.FileOutputStream;


@CrossOrigin(origins="*")
@RestController
public class AlfrescoAPIController
{
  public AlfrescoAPIController() {}
  
  DownloadTest download = new DownloadTest();
  
  @GetMapping("test")
  public String test() {
    return "test";
  }
  
  @GetMapping("connection")
  public void TestConnection() { Session session = CmisSessionSingleton.getInstance(); }
  
  @RequestMapping(value={"/"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public String test2() {
    return "test2";
  }
  
  @GetMapping("uploadfile")
  public void UploadDoc() throws ParserConfigurationException, SAXException, IOException {
    download.uploadFile();
  }
  
  @GetMapping("downloadfile")
  public void downloafile() throws Exception { download.getTree(); }
  
  @GetMapping("downloadfilemultiple")
  public void downloafilemultiple() throws Exception {
    download.DownloadMultiple();
  }
@GetMapping("visualizefile")
public void  visualize( @PathParam("pj ")String pj, @PathParam("ext") String ext) throws Exception {
	   download.Visualiser(pj, ext);
}
  
//  @GetMapping("visualizefile")
//  public void  visualize(HttpServletRequest req, HttpServletResponse res, @PathParam("pj ")String pj, @PathParam("ext") String ext) throws Exception {
//	  
//req.getRequestDispatcher("D:\\Profiles\\mousassi\\eclipse-workspaceLYA3\\HRAlfrescoCMISDV46\\fevrier.pdf" ).forward(req,res);
//  }
//  
  

  @GetMapping("Parse")
  public ArrayList parsing() throws ParserConfigurationException, SAXException, IOException {
    return DownloadTest.ParseXML();
  }
  
  @GetMapping("ParseForZip")
  public String parsepourzip() throws ParserConfigurationException, SAXException, IOException {
	  return DownloadTest.ParseXMLPOURZIP(); }

@GetMapping("sendfile")
public void sendfilefromhr(@PathParam("pj ")String pj, @PathParam("ext") String ext) throws ParserConfigurationException, SAXException, IOException { 
	 download.sendFile(pj, ext); }
}