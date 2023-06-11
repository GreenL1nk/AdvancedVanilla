package greenlink.advancedvanilla.professions;

import greenlink.advancedvanilla.professions.requirements.Requirements;

public record Level(int levelNumber, Requirements... requirements) {
}
