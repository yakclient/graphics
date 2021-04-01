package net.yakclient.opengl.gui;

import org.junit.jupiter.api.Test;

public class PropertyTesting {
    @Test
    public void testPropertyCreation() throws IllegalPropertyException {
        final GUIProperties build = getBuild();

        System.out.println(build);
    }

    private GUIProperties getBuild() throws IllegalPropertyException {
        return PropertyFactory.create().addProp("testing!", "this is a value").addProp("Another value", 10).addProp("Final one", "a value").build();
    }

    @Test
    public void testPropertyRetrieval() throws IllegalPropertyException {
        final GUIProperties build = this.getBuild();
        System.out.println(build.<String>get(0));
    }

    private void runSpeedTest(Runnable test, int iterations) {
        final long startNanos = System.nanoTime();

        for (int i = 0; i < iterations; i++) {
            final long cIteration = System.nanoTime();
            test.run();
            System.out.println("Test iteration: " + (i + 1) + " has been completed with a time of: " + (System.nanoTime() - cIteration));
        }

        System.out.println("Finished with a average of: " + ((System.nanoTime() - startNanos) / iterations) + " and a total of: " + (System.nanoTime() - startNanos));
    }

    //From 100,000 iterations the average is 999010 nano seconds
    @Test
    public void testAddPropFactorySpeed() {
        //To make this run you have to take out the check for same names.
        final PropertyFactory propertyFactory = PropertyFactory.create();
        this.runSpeedTest(() -> {
                        propertyFactory.addProp("test", "something");
        }, 100);
    }

    //In 10,000 iterations it did a average of 20106 nano seconds
    //In 100,000 iterations it did a average of 9109 ns.
    @Test
    public void testAddChildFactorySpeed() {
        final PropertyFactory propertyFactory = PropertyFactory.create();
        this.runSpeedTest(() -> {
            propertyFactory.addChild(null);
        }, 100000);
    }
}
