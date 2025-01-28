package cn.ksmcbrigade.ec.config;

import cn.ksmcbrigade.ca.config.Config;
import cn.ksmcbrigade.ca.config.ConfigBuilder;
import cn.ksmcbrigade.ec.ExampleClient;
import cn.ksmcbrigade.ec.module.Module;

import java.io.IOException;

public class ModuleConfig {
    public static Config enables_config = null;
    public static Config keys_config = null;

    public static void init() throws IOException {
        if(enables_config==null || keys_config==null){
            ConfigBuilder builder = new ConfigBuilder("ec-modules-enables.json",true);
            for (Module module : ExampleClient.MODULES) {
                builder.data.put(module.name,module.enable);
            }
            builder.setCallback((s,o)->{
                for (Module module : ExampleClient.MODULES) {
                    if(module.name.equalsIgnoreCase(s)){
                        if(o instanceof Boolean e && e!=module.enable) module.set(e);
                    }
                }
            });
            enables_config = builder.buildOnly();
            ConfigBuilder keys_builder = new ConfigBuilder("ec-modules-keys.json",true);
            for (Module module : ExampleClient.MODULES) {
                keys_builder.data.put(module.name,module.key);
            }
            keys_builder.setCallback((s,o)->{
                for (Module module : ExampleClient.MODULES) {
                    if(module.name.equalsIgnoreCase(s)){
                        if(o instanceof Integer k && k!=module.key) module.key = k;
                    }
                }
            });
            keys_config = keys_builder.buildOnly();

            enables_config.reload();
            keys_config.reload();
        }
    }
}
