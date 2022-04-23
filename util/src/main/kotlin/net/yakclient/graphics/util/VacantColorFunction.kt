@file:JvmName("VacantColorFunction")

package net.yakclient.graphics.util

import net.yakclient.graphics.util.buffer.safeFloatBufOf

public class VacantColorFunction : ColorFunction by functionalColorFunc({ _, _-> safeFloatBufOf() })