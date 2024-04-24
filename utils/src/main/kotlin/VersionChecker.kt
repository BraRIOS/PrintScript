class VersionChecker {
    fun versionIsSameOrOlderThanCurrentVersion(
        target: String,
        currentVersion: String,
    ): Boolean {
        val targetVersionSplit = target.split(".").map { it.toInt() }
        val currentVersionSplit = currentVersion.split(".").map { it.toInt() }
        val currentVersionIsNewer = currentVersionSplit.zip(targetVersionSplit).all { (current, target) -> current >= target }
        return currentVersionIsNewer
    }
}