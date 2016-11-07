package w15c2.tusk.commons.core;

import w15c2.tusk.commons.events.BaseEvent;

/**
 * Base class for *Manager classes
 *
 * Registers the class' event handlers in eventsCenter
 */
public abstract class ComponentManager {
    private EventsCenter eventsCenter;

    /**
     * Uses default {@link EventsCenter}
     */
    public ComponentManager(){
        this(EventsCenter.getInstance());
    }

    private ComponentManager(EventsCenter eventsCenter) {
        this.eventsCenter = eventsCenter;
        eventsCenter.registerHandler(this);
    }

    protected void raise(BaseEvent event){
        eventsCenter.post(event);
    }
    
}
