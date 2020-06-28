import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Guitar {


    //number of frets on the guitar
    Integer frets;
    //number of strings on the guitar
    Integer strings;
    //stores the tuning offset for each string
    Integer[] offsets;

    //maps from a numerical pitch to its scientific pitch notation
    Map<Integer, String> numToSci;
    Map<String, Integer> sciToNum;

    String[] sciPitchNotations = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
    //String[] sciPitchNotations = {"C", "Db", "D", "Eb", "E", "F", "Gb", "G", "Ab", "A", "Bb", "B"};

    public Guitar() {

        //0 being the open string and 1 being the first fret
        frets = 21;
        //0 being the lowest string and 5 being the highest string on a typical guitar
        strings = 6;
        //offset[0] = -2 would be a drop D tuning
        offsets = new Integer[strings];

        numToSci = new HashMap<>();

        for(int i = 0; i < 100; i++) {
            numToSci.put(i, sciPitchNotations[i % 12] + (i / 12));
            sciToNum.put(sciPitchNotations[i % 12] + (i / 12), i);
        }
    }

    //given a string and fret returns the numerical pitch
    public int getNote(int string, int fret) {
        int stringOffset = 5 * string;
        if(string >= 4) {
            stringOffset--;
        }
        return 28 + stringOffset + fret + offsets[string];
    }

    //given a numerical pitch return the scientific pitch notation
    public String getSciPitch(int numericalPitch) {
        return numToSci.get(numericalPitch);
    }

    public List<Integer[]> getFrets(int numericalPitch) {
        Integer[] ret = new Integer[6];
        for(int i = 0; i < ret.length; i++) {
            int stringOffset = 5 * i;
            if(i >= 4) {
                stringOffset--;
            }
            int fret = (numericalPitch - (28 + stringOffset + offsets[i])
            if( fret < ) {
                return
            }
        }
    }

    public int getFrets() {
        return frets;
    }

    public int getStrings() {
        return strings;
    }

}
