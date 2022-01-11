package hu.idne.backend.utils.system;

public final class CaseConverter {

    private CaseConverter() {
    }

    public static String upperCaseLowerHyphenToLowerCaseDotted(String input) {
        if (input == null) {
            return null;
        }
        return input.toLowerCase().replace("_", ".");
    }

}
