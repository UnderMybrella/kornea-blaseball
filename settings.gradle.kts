
rootProject.name = "kornea-blaseball"

include(":base", ":api")

rootProject.children.forEach { child -> child.name = "kornea-blaseball-${child.name}" }