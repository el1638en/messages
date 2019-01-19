package com.syscom

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder
import io.gatling.http.protocol.HttpProtocolBuilder.toHttpProtocol
import io.gatling.http.request.builder.HttpRequestBuilder.toActionBuilder
import scala.collection.Seq

class InitSimulation extends Simulation {

	private val baseUrl = "http://localhost:8080"

	private val uri = "/api/user"
	private val contentType = "application/json"
	private val endpoint = "/api/1.0/arrival/all"
	private val authUser= "blaze"
	private val requestCount = 1000

	val httpProtocol:HttpProtocolBuilder= http.baseURL(baseUrl)
											  .contentTypeHeader(contentType)
											  .userAgentHeader("Mozilla/5.0")

	val headers_0 = Map("Expect" -> "100-continue")

	val scn:ScenarioBuilder=

	scenario("RecordedSimulation")
			   .exec(http("request_0")
			   .post(uri)
  			   .formParamSeq(Seq(("name", "toto"), ("firstName", "tototo"), ("login", "loginDD"), ("password", "password"), ("role", "USERS")))
			   .headers(headers_0)
               .check(status.is(200)))

	setUp(scn.inject(atOnceUsers(requestCount))).protocols(httpProtocol)

}
