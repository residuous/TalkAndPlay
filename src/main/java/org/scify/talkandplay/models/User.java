/**
* Copyright 2016 SciFY
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package org.scify.talkandplay.models;

import org.scify.talkandplay.models.modules.CommunicationModule;
import org.scify.talkandplay.models.modules.EntertainmentModule;
import org.scify.talkandplay.models.modules.GameModule;

public class User {

    private String name;
    private String image;
    private boolean preselected;

    private Configuration configuration;
    private CommunicationModule communicationModule;
    private EntertainmentModule entertainmentModule;
    private GameModule gameModule;

    public User() {
        configuration = new Configuration();
        communicationModule = new CommunicationModule();
        entertainmentModule = new EntertainmentModule();
        gameModule = new GameModule();
    }

    public User(String name) {
        this.name = name;
    }

    public User(String name, String image) {
        this.name = name;
        this.image = image;
        configuration = new Configuration();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isPreselected() {
        return preselected;
    }

    public void setPreselected(boolean preselected) {
        this.preselected = preselected;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public CommunicationModule getCommunicationModule() {
        return communicationModule;
    }

    public void setCommunicationModule(CommunicationModule communicationModule) {
        this.communicationModule = communicationModule;
    }

    public EntertainmentModule getEntertainmentModule() {
        return entertainmentModule;
    }

    public void setEntertainmentModule(EntertainmentModule entertainmentModule) {
        this.entertainmentModule = entertainmentModule;
    }

    public GameModule getGameModule() {
        return gameModule;
    }

    public void setGameModule(GameModule gameModule) {
        this.gameModule = gameModule;
    }

}
