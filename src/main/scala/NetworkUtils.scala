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
   * This method does not use a timeout, so it probably shouldn't be used in
   * production projects.
   * Usage: val content = getRestContent("http://localhost:8080/getStocks")
   */
  @throws(classOf[java.io.IOException])
  def getRestContent(url: String) = io.Source.fromURL(url).mkString

  /**
   * Returns the text (content) from a REST URL as a String.
   * Inspired by http://matthewkwong.blogspot.com/2009/09/scala-scalaiosource-fromurl-blockshangs.html
   * and http://www.devdaily.com/blog/post/java/how-open-url-read-contents-httpurl-connection-java
   * The timeout value is used for both the setConnectTimeout and setReadTimeout Connection methods.
   * As a practical matter, if a service is down, the method will return in timeout ms.
   * Usage: val content = getRestContent("http://localhost:8080/getStocks", 5000)
   */
  @throws(classOf[java.io.IOException])
  @throws(classOf[java.net.SocketTimeoutException])
  def getRestContent(url: String, timeout: Int):String = {
    import java.net.{URL, HttpURLConnection}
    val connection = (new URL(url)).openConnection.asInstanceOf[HttpURLConnection]
    connection.setConnectTimeout(timeout)
    connection.setReadTimeout(timeout)
    connection.setRequestMethod("GET")
    val inputStream = connection.getInputStream
    val content = io.Source.fromInputStream(inputStream).mkString
    inputStream.close
    content
  }

}


