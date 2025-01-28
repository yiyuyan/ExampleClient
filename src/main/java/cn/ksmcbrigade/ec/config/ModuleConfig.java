package cn.ksmcbrigade.ec.config;

import cn.ksmcbrigade.ca.config.Config;
import cn.ksmcbrigade.ca.config.ConfigBuilder;
import cn.ksmcbrigade.ec.ExampleClient;
import cn.ksmcbrigade.ec.module.Module;

import java.io.IOException;

public class ModuleConfig {
    public static Config enables_config = null;
    public static Config keys_config = null;

    public static void init() throws IOException, NoSuchFieldException, IllegalAccessException {
        if(enables_config==null || keys_config==null){
            ConfigBuilder builder = new ConfigBuilder("ec-modules-enables.json",true);
            for (Module module : ExampleClient.MODULES) {
                builder.data.put(module.name,module.enable);
            }
            enables_config = builder.build();
            ConfigBuilder keys_builder = new ConfigBuilder("ec-modules-keys.json",true);
            for (Module module : ExampleClient.MODULES) {
                keys_builder.data.put(module.name,module.key);
            }
            keys_config = keys_builder.build();
        }
    }
}
