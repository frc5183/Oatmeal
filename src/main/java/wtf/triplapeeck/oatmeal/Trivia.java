package wtf.triplapeeck.oatmeal;

import java.util.List;

/**
 * @deprecated if this is ever implemented, it will be in a different way and is just unused as of now
 */
@Deprecated
public class Trivia {
    public String question;
    public List<String> answers;
    public Trivia(String quest, List<String> ans) {
        question=quest; answers=ans;
    }
}
