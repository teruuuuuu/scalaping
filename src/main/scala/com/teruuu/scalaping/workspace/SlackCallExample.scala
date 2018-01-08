package com.teruuu.scalaping.workspace

import com.teruuu.scalaping.config.ApplicationConfig
import com.teruuu.scalaping.slack.SlackCaller

object SlackCallExample {

  def main(args: Array[String]) = {
    SlackCaller.call(ApplicationConfig.slack_token, ApplicationConfig.slack_channel, "test message")
  }

}
