package net.nitrado.api.services;

import com.google.gson.JsonElement;
import net.nitrado.api.Nitrapi;
import net.nitrado.api.common.exceptions.NitrapiErrorException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * A class to create a specific service-class from json data.
 */
public class ServiceFactory {
    /**
     * Returns an object of the specific subclass for the given service.
     *
     * @param api  handle of the api
     * @param data json-data containing a single service object
     * @return an object of a subclass of service
     */
    public static Service factory(Nitrapi api, JsonElement data) {

        // build the class name for the service
        String type = data.getAsJsonObject().get("type").getAsString();
        String className = "net.nitrado.api.services." + type.toLowerCase() + "s." + type.substring(0, 1).toUpperCase() + type.substring(1).toLowerCase();

        try {

            // create the base service object first
            Service service = (Service) api.fromJson(data, Class.forName(className));
            service.init(api);

            return service;

        } catch (ClassNotFoundException e) {
            throw new NitrapiErrorException("Service " + className + " not supported.", -1);
        } catch (ClassCastException e) {
            throw new NitrapiErrorException(className + " has to be a subclass of Service.", -1);
        }
    }
}
