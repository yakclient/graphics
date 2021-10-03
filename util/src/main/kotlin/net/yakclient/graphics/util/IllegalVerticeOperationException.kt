package net.yakclient.graphics.util

public class IllegalVerticeOperationException(attempt: String) : TemplatedException("This is not a valid operation! Exception: '%s'", attempt)