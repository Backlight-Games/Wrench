package games.backlight.wrench.listeners.automod.modules;

import net.dv8tion.jda.api.entities.Message;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Module {
	void run();
	boolean check();
}
