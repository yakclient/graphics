package net.yakclient.graphics.util

import java.nio.Buffer
import java.util.ArrayList

public object YakGraphicsUtils {
    public const val MAX_DOUBLE_CLICK_TIME: Int = 500
    public const val MOUSE_LEFT_BUTTON: Int = 0
    public const val MOUSE_RIGHT_BUTTON: Int = 1
    public const val MOUSE_MIDDLE_BUTTON: Int = 2
    public const val CHAR_NONE: Int = 0
    public const val KEY_NONE: Int = 0
    public const val KEY_ESCAPE: Int = 1
    public const val KEY_1: Int = 2
    public const val KEY_2: Int = 3
    public const val KEY_3: Int = 4
    public const val KEY_4: Int = 5
    public const val KEY_5: Int = 6
    public const val KEY_6: Int = 7
    public const val KEY_7: Int = 8
    public const val KEY_8: Int = 9
    public const val KEY_9: Int = 10
    public const val KEY_0: Int = 11
    public const val KEY_MINUS: Int = 12
    public const val KEY_EQUALS: Int = 13
    public const val KEY_BACK: Int = 14
    public const val KEY_TAB: Int = 15
    public const val KEY_Q: Int = 16
    public const val KEY_W: Int = 17
    public const val KEY_E: Int = 18
    public const val KEY_R: Int = 19
    public const val KEY_T: Int = 20
    public const val KEY_Y: Int = 21
    public const val KEY_U: Int = 22
    public const val KEY_I: Int = 23
    public const val KEY_O: Int = 24
    public const val KEY_P: Int = 25
    public const val KEY_LBRACKET: Int = 26
    public const val KEY_RBRACKET: Int = 27
    public const val KEY_RETURN: Int = 28
    public const val KEY_LCONTROL: Int = 29
    public const val KEY_A: Int = 30
    public const val KEY_S: Int = 31
    public const val KEY_D: Int = 32
    public const val KEY_F: Int = 33
    public const val KEY_G: Int = 34
    public const val KEY_H: Int = 35
    public const val KEY_J: Int = 36
    public const val KEY_K: Int = 37
    public const val KEY_L: Int = 38
    public const val KEY_SEMICOLON: Int = 39
    public const val KEY_APOSTROPHE: Int = 40
    public const val KEY_GRAVE: Int = 41
    public const val KEY_LSHIFT: Int = 42
    public const val KEY_BACKSLASH: Int = 43
    public const val KEY_Z: Int = 44
    public const val KEY_X: Int = 45
    public const val KEY_C: Int = 46
    public const val KEY_V: Int = 47
    public const val KEY_B: Int = 48
    public const val KEY_N: Int = 49
    public const val KEY_M: Int = 50
    public const val KEY_COMMA: Int = 51
    public const val KEY_PERIOD: Int = 52
    public const val KEY_SLASH: Int = 53
    public const val KEY_RSHIFT: Int = 54
    public const val KEY_MULTIPLY: Int = 55
    public const val KEY_LMENU: Int = 56
    public const val KEY_SPACE: Int = 57
    public const val KEY_CAPITAL: Int = 58
    public const val KEY_F1: Int = 59
    public const val KEY_F2: Int = 60
    public const val KEY_F3: Int = 61
    public const val KEY_F4: Int = 62
    public const val KEY_F5: Int = 63
    public const val KEY_F6: Int = 64
    public const val KEY_F7: Int = 65
    public const val KEY_F8: Int = 66
    public const val KEY_F9: Int = 67
    public const val KEY_F10: Int = 68
    public const val KEY_NUMLOCK: Int = 69
    public const val KEY_SCROLL: Int = 70
    public const val KEY_NUMPAD7: Int = 71
    public const val KEY_NUMPAD8: Int = 72
    public const val KEY_NUMPAD9: Int = 73
    public const val KEY_SUBTRACT: Int = 74
    public const val KEY_NUMPAD4: Int = 75
    public const val KEY_NUMPAD5: Int = 76
    public const val KEY_NUMPAD6: Int = 77
    public const val KEY_ADD: Int = 78
    public const val KEY_NUMPAD1: Int = 79
    public const val KEY_NUMPAD2: Int = 80
    public const val KEY_NUMPAD3: Int = 81
    public const val KEY_NUMPAD0: Int = 82
    public const val KEY_DECIMAL: Int = 83
    public const val KEY_F11: Int = 87
    public const val KEY_F12: Int = 88
    public const val KEY_F13: Int = 100
    public const val KEY_F14: Int = 101
    public const val KEY_F15: Int = 102
    public const val KEY_F16: Int = 103
    public const val KEY_F17: Int = 104
    public const val KEY_F18: Int = 105
    public const val KEY_KANA: Int = 112
    public const val KEY_F19: Int = 113
    public const val KEY_CONVERT: Int = 121
    public const val KEY_NOCONVERT: Int = 123
    public const val KEY_YEN: Int = 125
    public const val KEY_NUMPADEQUALS: Int = 141
    public const val KEY_CIRCUMFLEX: Int = 144
    public const val KEY_AT: Int = 145
    public const val KEY_COLON: Int = 146
    public const val KEY_UNDERLINE: Int = 147
    public const val KEY_KANJI: Int = 148
    public const val KEY_STOP: Int = 149
    public const val KEY_AX: Int = 150
    public const val KEY_UNLABELED: Int = 151
    public const val KEY_NUMPADENTER: Int = 156
    public const val KEY_RCONTROL: Int = 157
    public const val KEY_SECTION: Int = 167
    public const val KEY_NUMPADCOMMA: Int = 179
    public const val KEY_DIVIDE: Int = 181
    public const val KEY_SYSRQ: Int = 183
    public const val KEY_RMENU: Int = 184
    public const val KEY_FUNCTION: Int = 196
    public const val KEY_PAUSE: Int = 197
    public const val KEY_HOME: Int = 199
    public const val KEY_UP: Int = 200
    public const val KEY_PRIOR: Int = 201
    public const val KEY_LEFT: Int = 203
    public const val KEY_RIGHT: Int = 205
    public const val KEY_END: Int = 207
    public const val KEY_DOWN: Int = 208
    public const val KEY_NEXT: Int = 209
    public const val KEY_INSERT: Int = 210
    public const val KEY_DELETE: Int = 211
    public const val KEY_CLEAR: Int = 218
    public const val KEY_LMETA: Int = 219

    @Deprecated("")
    public val KEY_LWIN: Int = 219
    public const val KEY_RMETA: Int = 220

    @Deprecated("")
    public val KEY_RWIN: Int = 220
    public const val KEY_APPS: Int = 221
    public const val KEY_POWER: Int = 222
    public const val KEY_SLEEP: Int = 223
    public const val KEYBOARD_SIZE: Int = 256

    public fun <T> defaultCollection(): Collection<T> = ArrayList()

    public fun <T : Buffer> flipBuf(buf: T): T = buf.apply { flip() }

//    public fun toPrimitiveArray(initial: Array<Double>): DoubleArray {
//        val allocation = DoubleArray(initial.size)
//        for (i in initial.indices) {
//            allocation[i] = initial[i]
//        }
//        return allocation
//    }
//
//    fun toPrimitiveArray(initial: Array<Float>): FloatArray {
//        val allocation = FloatArray(initial.size)
//        for (i in initial.indices) {
//            allocation[i] = initial[i]
//        }
//        return allocation
//    }
//
//    fun toPrimitiveArray(initial: Array<Char>): CharArray {
//        val allocation = CharArray(initial.size)
//        for (i in initial.indices) {
//            allocation[i] = initial[i]
//        }
//        return allocation
//    }
//
//    fun toPrimitiveArray(initial: Array<Short>): ShortArray {
//
//        val allocation = ShortArray(initial.size)
//        for (i in initial.indices) {
//            allocation[i] = initial[i]
//        }
//        return allocation
//    }
//
//    fun toPrimitiveArray(initial: Array<Int>): IntArray {
//        val allocation = IntArray(initial.size)
//        for (i in initial.indices) {
//            allocation[i] = initial[i]
//        }
//        return allocation
//    }
//
//    fun toPrimitiveArray(initial: Array<Long>): LongArray {
//        val allocation = LongArray(initial.size)
//        for (i in initial.indices) {
//            allocation[i] = initial[i]
//        }
//        return allocation
//    }
//
//    fun toPrimitiveArray(initial: Array<Boolean>): BooleanArray {
//        val allocation = BooleanArray(initial.size)
//        for (i in initial.indices) {
//            allocation[i] = initial[i]
//        }
//        return allocation
//    }
}