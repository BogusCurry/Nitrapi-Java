package net.nitrado.api.services.voiceservers;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import net.nitrado.api.Nitrapi;
import net.nitrado.api.common.http.Parameter;
import net.nitrado.api.services.Service;
import net.nitrado.api.services.bouncers.BouncerInstance;

/**
 * This class represents a voice server.
 */
public class Voiceserver extends Service {
    private VoiceserverInfo info;

    class VoiceserverInfo {
        private Type type;
        private String ip;
        private int port;
        private int slots;
        private boolean started;
        private JsonObject specific;
    }

    public enum Type {
        @SerializedName("teamspeak3")
        TEAMSPEAK3,
        @SerializedName("mumble")
        MUMBLE,
        @SerializedName("ventrilo")
        VENTRILO
    }

    public Type getType() {
        return info!=null?info.type:null;
    }
    public String getIp() {
        return info!=null?info.ip:"";
    }
    public int getPort() {
        return info!=null?info.port:0;
    }
    public int getSlots() {
        return info!=null?info.slots:0;
    }
    public boolean isStarted() {
        return info!=null?info.started:false;
    }

    @Override
    public void refresh() {
        JsonObject data = api.dataGet("services/" + getId() + "/voiceservers", null);
        VoiceserverInfo infos = api.fromJson(data.get("voiceserver"), VoiceserverInfo.class);
        this.info = infos;
    }

    /**
     * Restarts the voiceserver.
     * @permission ROLE_WEBINTERFACE_GENERAL_CONTROL
     */
    public void doRestart() {
        api.dataPost("services/" + getId() + "/voiceservers/restart", null);
    }

    /**
     * Stopps the voiceserver. Notice: Not supported by Mumble.
     * @permission ROLE_WEBINTERFACE_GENERAL_CONTROL
     */
    public void doStop() {
        api.dataPost("services/" + getId() + "/voiceservers/stop", null);
    }

    /**
     * Reinstalls the voiceserver.
     * @permission ROLE_WEBINTERFACE_GENERAL_CONTROL
     */
    public void doReinstall() {
        api.dataPost("services/" + getId() + "/voiceservers/reinstall", null);
    }
}
