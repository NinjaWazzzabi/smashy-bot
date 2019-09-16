package database

import discord4j.core.`object`.entity.User
import user.BotUser

interface Database {

    fun getBotUser(user : User) : BotUser
    fun getBotUsers(): List<BotUser>
    fun setBotUser(botUser : BotUser): Boolean
    fun removeBotUser(botUser : BotUser): Boolean


}
