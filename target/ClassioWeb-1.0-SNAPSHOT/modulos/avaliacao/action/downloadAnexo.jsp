<%-- 
    Document   : downloadAnexo
    Created on : 06/10/2018, 15:12:54
    Author     : vitor_9g3eyo1
--%>

<%@page import="java.io.OutputStream"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.sql.Blob"%>
<%@page import="br.com.classio.model.dao.AvaliacaoDAO"%>
<%@page import="br.com.classio.model.beans.Avaliacao"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    int BUFFER_SIZE = 4096;   
    Avaliacao a = new Avaliacao();
    AvaliacaoDAO adao = new AvaliacaoDAO();
    a = adao.consultaId(Integer.parseInt(request.getParameter("avacodigo")));
    InputStream inputStream = a.getAvaanexo();
    int fileLength = inputStream.available();
    System.out.println("fileLength = " + fileLength);
    String fileName =  "anexo_"+a.getAvadescricao()+".pdf";
    ServletContext context = getServletContext();
    // sets MIME type for the file download
    String mimeType = context.getMimeType(fileName);
    if (mimeType == null) {
        mimeType = "application/octet-stream";
    }
    // set content properties and header attributes for the response
    response.setContentType(mimeType);
    response.setContentLength(fileLength);
    String headerKey = "Content-Disposition";
    String headerValue = String.format("attachment; filename=\"%s\"", fileName);
    response.setHeader(headerKey, headerValue);
    // writes the file to the client
    OutputStream outStream = response.getOutputStream();
    byte[] buffer = new byte[BUFFER_SIZE];
    int bytesRead = -1;
    while ((bytesRead = inputStream.read(buffer)) != -1) {
        outStream.write(buffer, 0, bytesRead);
    }
    inputStream.close();
    outStream.close();
%>