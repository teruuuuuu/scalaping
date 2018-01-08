package com.teruuu.scalaping.workspace

import akka.actor.ActorSystem
import slack.SlackUtil
import slack.rtm.SlackRtmClient
import java.io._

import org.apache.commons._
import org.apache.http._
import org.apache.http.client._
import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.client.DefaultHttpClient
import java.util.ArrayList

import com.teruuu.scalaping.config.ApplicationConfig
import com.teruuu.scalaping.slack.SlackCaller
import org.apache.http.message.BasicNameValuePair
import org.apache.http.client.entity.UrlEncodedFormEntity


object SlackBotClientExample extends App {

  implicit val system = ActorSystem("slack")
  implicit val ec = system.dispatcher
  val client = SlackRtmClient(ApplicationConfig.slack_token)
  val selfId = client.state.self.id


  client.onMessage { message =>
    client.sendMessage(message.channel, "test message")

    val mentionedIds = SlackUtil.extractMentionedIds(message.text)
    if(mentionedIds.contains(selfId)) {
      client.sendMessage(message.channel, s"<@${message.user}>: Hey!")
    }
  }
}
