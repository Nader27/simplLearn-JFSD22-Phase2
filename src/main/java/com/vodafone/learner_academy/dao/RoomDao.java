package com.vodafone.learner_academy.dao;

import com.vodafone.learner_academy.model.Room;

public class RoomDao extends AbstractDao<Room> {
    @Override
    protected void seedDB() {
        save(new Room("Room A",25));
        save(new Room("Room B",10));
        save(new Room("Room C",30));
    }
}
