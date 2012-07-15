package com.alvinalexander.network

import java.io._
import org.apache.http.{HttpEntity, HttpResponse}
import org.apache.http.client._
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.DefaultHttpClient
import scala.collection.mutable.StringBuilder
import scala.xml.XML
import org.apache.http.params.HttpConnectionParams
import org.apache.http.params.HttpParams

object NetworkUtils {

  /**
   * Returns the text (content) from a REST URL as a String.
   * Returns a blank String if there was a problem.
   * This function will also throw exceptions if there are problems trying
   * to connect to the url.
   *
   * @param url A complete URL, such as "http://foo.com/bar"
   */
  def getRestContent(url: String): String = {
    val httpClient = new DefaultHttpClient
    val httpResponse = httpClient.execute(new HttpGet(url))
    val entity = httpResponse.getEntity
    var content = ""
    if (entity != null) {
      val inputStream = entity.getContent
      content = io.Source.fromInputStream(inputStream).getLines.mkString
      inputStream.close
    }
    httpClient.getConnectionManager.shutdown
    return content
  }


  /**
   * Returns the text (content) from a REST URL as a String.
   * Returns a blank String if there was a problem.
   * This function will also throw exceptions if there are problems trying
   * to connect to the url.
   * 
   * @param url A complete URL, such as "http://foo.com/bar"
   * @param connectionTimeout The connection timeout, in ms.
   * @param socketTimeout The socket timeout, in ms.
   */
  def getRestContent(url: String, connectionTimeout: Int, socketTimeout: Int): String = {
    val httpClient = buildHttpClient(connectionTimeout, socketTimeout)
    val httpResponse = httpClient.execute(new HttpGet(url))
    val entity = httpResponse.getEntity
    var content = ""
    if (entity != null) {
      val inputStream = entity.getContent
      content = io.Source.fromInputStream(inputStream).getLines.mkString
      inputStream.close
    }
    httpClient.getConnectionManager.shutdown
    return content
  }
  
  private def buildHttpClient(connectionTimeout: Int, socketTimeout: Int): DefaultHttpClient = {
    val httpClient = new DefaultHttpClient
    val httpParams = httpClient.getParams
    HttpConnectionParams.setConnectionTimeout(httpParams, connectionTimeout)
    HttpConnectionParams.setSoTimeout(httpParams, socketTimeout)
    httpClient.setParams(httpParams)
    httpClient
  }



}


