/*
 * Copyright 2020 Mamoe Technologies and contributors.
 *
 * 此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU AGPLv3 license that can be found through the following link.
 *
 * https://github.com/mamoe/mirai/blob/master/LICENSE
 */

@file:JvmMultifileClass
@file:JvmName("MessageUtils")

package net.mamoe.mirai.message.data

import net.mamoe.mirai.utils.MiraiInternalAPI
import net.mamoe.mirai.utils.SinceMirai
import kotlin.jvm.JvmMultifileClass
import kotlin.jvm.JvmName
import kotlin.jvm.JvmSynthetic

private const val displayA = "@全体成员"

/**
 * "@全体成员".
 *
 * 非会员每天只能发送 10 次 [AtAll]. 超出部分会被以普通文字看待.
 *
 * @see At at 单个群成员
 */
object AtAll :
    Message.Key<AtAll>,
    MessageContent,
    CharSequence by displayA,
    Comparable<String> by displayA {

    @SinceMirai("0.31.2")
    const val display = displayA
    override val typeName: String
        get() = "AtAll"

    @Suppress("SpellCheckingInspection")
    override fun toString(): String = "[mirai:atall]"
    override fun contentToString(): String = display

    // 自动为消息补充 " "
    @OptIn(MiraiInternalAPI::class)
    @Deprecated("for binary compatibility", level = DeprecationLevel.HIDDEN)
    @Suppress("INAPPLICABLE_JVM_NAME", "EXPOSED_FUNCTION_RETURN_TYPE")
    @JvmName("followedBy")
    @JvmSynthetic
    override fun followedBy1(tail: Message): CombinedMessage {
        return followedByInternalForBinaryCompatibility(tail)
    }

    override fun followedBy(tail: Message): MessageChain {
        if (tail is PlainText && tail.stringValue.startsWith(' ')) {
            return super.followedBy(tail)
        }
        return super.followedBy(PlainText(" ")) + tail
    }
}