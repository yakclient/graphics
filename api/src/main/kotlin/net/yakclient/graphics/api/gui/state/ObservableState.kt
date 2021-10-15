package net.yakclient.graphics.api.gui.state

import kotlin.properties.Delegates

public class ObservableState<T>(
    initialValue: T,
    observer: (old: T, new: T) -> Unit
) : Stateful<T> {
    public override var value: T by Delegates.observable(initialValue) { _, old, new -> observer(old, new) }
}