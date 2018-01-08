package com.teruuu.scalaping.config

import com.typesafe.config.ConfigFactory

object ApplicationConfig {
  val config = ConfigFactory.load
  Class.forName(config.getString("db.default.driver"))
  val db_url = config.getString("db.default.url")
  val db_user = config.getString("db.default.user")
  val db_password = config.getString("db.default.password")

  val slack_token = config.getString("slack.token")
  val slack_channel = config.getString("slack.channel")
}
