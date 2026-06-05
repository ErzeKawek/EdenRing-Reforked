package paulevs.edenring.misc;

import com.google.common.collect.Maps;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class JsonUtil {
    private static final Map<Identifier, String> JSON_CACHE = Maps.newConcurrentMap();

    public static Optional<String> createJson(Identifier patternId, Identifier blockId) {
        Map<String, String> textures = Maps.newHashMap();
        textures.put("%modid%", blockId.getNamespace());
        textures.put("%texture%", blockId.getPath());
        return createJson(patternId, textures);
    }

    public static Optional<String> createJson(Identifier patternId, Map<String, String> textures) {
        ResourceManager resourceManager = Minecraft.getInstance().getResourceManager();
        Optional<Resource> patternRes = resourceManager.getResource(patternId);
        if (patternRes.isEmpty()) return Optional.empty();

        try (InputStream input = patternRes.get().open()) {
            String json = JSON_CACHE.get(patternId);
            if (json == null) {
                json = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8)).lines()
                        .collect(Collectors.joining());
                JSON_CACHE.put(patternId, json);
            }
            for (Map.Entry<String, String> texture : textures.entrySet()) {
                json = json.replace(texture.getKey(), texture.getValue());
            }
            return Optional.of(json);
        } catch (Exception ex) {
            return Optional.empty();
        }
    }
}
