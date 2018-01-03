package com.teruuu.scalaping.db.config

import scalikejdbc.{GlobalSettings, LoggingSQLAndTimeSettings}


trait LogSettings {
  GlobalSettings.loggingSQLAndTime = LoggingSQLAndTimeSettings(
    // enabled = true,
    enabled = false,
    logLevel = 'DEBUG,
    // logLevel = 'ERROR,
    // warningEnabled = true,
    warningEnabled = false,
    warningThresholdMillis = 1000L,
    warningLogLevel = 'WARN
  )
}
