package net.yakclient.graphics.components.test

import net.yakclient.graphics.api.hook.HookTickManager
import org.lwjgl.LWJGLException
import org.lwjgl.opengl.Display
import org.lwjgl.opengl.DisplayMode
import org.lwjgl.opengl.GL11
import kotlin.system.exitProcess

object OpenGLSetup {
    private const val FPS_GOAL = 30
    private const val LONGEST_RUNTIME = 600 /* seconds */

    fun setupAndStart(gameLoop: () -> Unit) {
        setupAndStart({}, gameLoop)
    }

    fun setupAndStart(configurations: () -> Unit, gameLoop: () -> Unit) {
        try {
            Display.setDisplayMode(DisplayMode(1000, 1000))
            Display.setResizable(true)
            Display.create()
        } catch (e: LWJGLException) {
            println("FAILED: $e")
            exitProcess(1)
        }
        configurations()
        GL11.glMatrixMode(GL11.GL_PROJECTION)
        GL11.glLoadIdentity()
        //        GLU.gluPerspective(100, 640f/480f, 0.001f, 100f);
        GL11.glOrtho(0.0, Display.getWidth().toDouble(), Display.getHeight().toDouble(), 0.0, 0.0, -1.0)
        GL11.glMatrixMode(GL11.GL_MODELVIEW)
        GL11.glLoadIdentity()
        var highestFPS = 0
        var averageFPS = 0
        val startTime = System.currentTimeMillis()
        var lastSecond = System.currentTimeMillis()
        var fps = 0
        while (!Display.isCloseRequested() && System.currentTimeMillis() - startTime < LONGEST_RUNTIME * 1000) {

            if (System.currentTimeMillis() - lastSecond >= 1000) {
                lastSecond = System.currentTimeMillis()
                Display.setTitle("Current FPS: $fps")
                if (fps > highestFPS) highestFPS = fps
                averageFPS = (averageFPS + fps) / 2
                fps = 0
            } else fps++

            gameLoop()
            HookTickManager.tickThem()
            Display.update()
            Display.sync(FPS_GOAL)
        }
//        HookTickManager.shouldTick = false
        println("Attempting to cleanup resources")
        println()
        println("----- RUNTIME STATS -----")
        println("HIGHEST FPS: $highestFPS")
        println("AVERAGE FPS: $averageFPS")
        Display.destroy()
    }
}