import java.util.HashMap;
import java.util.List;
import java.util.Map;

// this class uses standard scientific pitch notation
// this class also uses a proprietary numerical pitch system where C0 is 0
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
    Map<Integer, String> numToSimp;

    //maps from a numerical pitch to its scientific pitch notation but flat
    Map<Integer, String> numToSciF;
    Map<String, Integer> sciToNumF;
    Map<Integer, String> numToSimpF;

    //maps from a chord to a list of its included notes
    // eg "Major Triad" -> [0, 4, 7]
    Map<String, Integer[]> chords;
    //maps from a scale to a list of its included notes
    // eg "Major Scale" -> [0, 2, 4, 5, 7, 9, 11]
    Map<String, Integer[]> scales;

    String[] sciPitchNotations = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
    String[] sciPitchNotationsF = {"C", "Db", "D", "Eb", "E", "F", "Gb", "G", "Ab", "A", "Bb", "B"};

    String[] intervals;

    public Guitar() {

        //0 being the open string and 1 being the first fret
        frets = 21;
        //0 being the lowest string and 5 being the highest string on a typical guitar
        strings = 6;
        //offset[0] = -2 would be a drop D tuning
        offsets = new Integer[strings];
        for (int i = 0; i < offsets.length; i++) {
            offsets[i] = 0;
        }

        numToSci = new HashMap<>();
        sciToNum = new HashMap<>();
        numToSimp = new HashMap<>();

        numToSciF = new HashMap<>();
        sciToNumF = new HashMap<>();
        numToSimpF = new HashMap<>();

        chords = createChordsMap();
        scales = createScalesMap();
        intervals = createIntervalsMap();

        //TODO: add negative octaves by subtracting 11
        for(int i = 0; i < 100; i++) {
            numToSci.put(i, sciPitchNotations[i % 12] + (i / 12));
            sciToNum.put(sciPitchNotations[i % 12] + (i / 12), i);
            numToSimp.put(i, sciPitchNotations[i % 12]);

            numToSciF.put(i, sciPitchNotationsF[i % 12] + (i / 12));
            sciToNumF.put(sciPitchNotationsF[i % 12] + (i / 12), i);
            numToSimpF.put(i, sciPitchNotationsF[i % 12]);
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

    //given a numerical pitch return the simple pitch notation
    public String getSimplePitch(int numericalPitch) { return numToSimp.get(numericalPitch); }

    //given a numerical pitch return the scientific pitch notation
    public String getSciPitchF(int numericalPitch) {
        return numToSciF.get(numericalPitch);
    }

    //given a numerical pitch return the simple pitch notation
    public String getSimplePitchF(int numericalPitch) { return numToSimpF.get(numericalPitch); }

    //given a numerical pitch returns what fret it is on on each string
    //if it can not on a string
    public Integer[] getFrets(int numericalPitch) {
        Integer[] ret = new Integer[strings];
        //iterate through strings
        for(int i = 0; i < ret.length; i++) {
            int stringOffset = 5 * i;
            if(i >= 4) {
                stringOffset--;
            }
            // if i played this note on this string what fret would it be on
            int fret = (numericalPitch - (28 + stringOffset + offsets[i]));
            // is the fret negative or greater than actual num of frets
            if( fret <=  frets && fret >= 0) {
                ret[i] = fret;
            } else {
                ret[i] = -1;
            }
        }
        return ret;
    }

    public int getFrets() {
        return frets;
    }

    public int getStrings() {
        return strings;
    }

    public Map<String, Integer[]> getChords() { return chords;}

    public Map<String, Integer[]> getScales() { return scales;}

    public String[] getIntervals() {return intervals;}

    public Map<String, Integer[]> createChordsMap() {
        Map<String, Integer[]> ret = new HashMap();

        ret.put("Augmented Triad", new Integer[]{0, 4, 8});
        ret.put("Augmented Eleventh", new Integer[]{0, 4, 7, 10, 2, 6});
        ret.put("Augmented Major Seventh", new Integer[]{0, 4, 8, 11});
        ret.put("Augmented Seventh", new Integer[]{0, 4, 8, 10});
        ret.put("Diminished Triad", new Integer[]{0, 3, 6});
        ret.put("Diminished Major Seventh", new Integer[]{0, 3, 6, 11});
        ret.put("Diminished Seventh", new Integer[]{0, 3, 6, 9});
        ret.put("Dominant Eleventh", new Integer[]{0, 4, 7, 10, 2, 5});
        ret.put("Dominant Minor Ninth", new Integer[]{0, 4, 7, 10, 1});
        ret.put("Dominant Ninth", new Integer[]{0, 4, 7, 10, 2});
        ret.put("Dominant Seventh", new Integer[]{0, 4, 7, 10});
        ret.put("Dominant Seventh Flat Five", new Integer[]{0, 4, 6, 10});
        ret.put("Dominant Seventh Sharp Nine", new Integer[]{0, 4, 7, 10, 3});
        ret.put("Dominant Thirteenth", new Integer[]{0, 4, 7, 10, 2, 5, 9});
        ret.put("Dream Chord", new Integer[]{0, 5, 6, 7});
        ret.put("Elektra Chord", new Integer[]{0, 7, 9, 1, 4});
        ret.put("Farben Chord", new Integer[]{0, 8, 11, 4, 9});
        ret.put("Half Diminished Seventh", new Integer[]{0, 3, 6, 10});
        ret.put("Harmonic Seventh Chord", new Integer[]{0, 4, 7, 10});
        ret.put("Leading Tone Triad", new Integer[]{0, 3, 6});
        ret.put("Lydian Chord", new Integer[]{0, 4, 7, 11, 6});
        ret.put("Magic Chord", new Integer[]{0, 1, 5, 6, 10, 0, 3, 5});
        ret.put("Major Triad", new Integer[]{0, 4, 7});
        ret.put("Major Eleventh", new Integer[]{0, 4, 7, 11, 2, 5});
        ret.put("Major Ninth", new Integer[]{0, 4, 7, 11, 2});
        ret.put("Major Seventh", new Integer[]{0, 4, 7, 11});
        ret.put("Major Seventh Sharp Eleventh", new Integer[]{0, 4, 7, 11, 6});
        ret.put("Major Sixth", new Integer[]{0, 4, 7, 9});
        ret.put("Major Sixth Ninth", new Integer[]{0, 4, 7, 9, 2});
        ret.put("Major Thirteenth", new Integer[]{0, 4, 7, 11, 2, 6, 9});
        ret.put("Mediant", new Integer[]{0, 3, 7});
        ret.put("Minor Triad", new Integer[]{0, 3, 7});
        ret.put("Minor Eleventh", new Integer[]{0, 3, 7, 10, 2, 5});
        ret.put("Minor Major Seventh", new Integer[]{0, 3, 7, 11});
        ret.put("Minor Ninth", new Integer[]{0, 3, 7, 10, 2});
        ret.put("Minor Seventh", new Integer[]{0, 3, 7, 10});
        ret.put("Minor Sixth", new Integer[]{0, 3, 7, 9});
        ret.put("Minor Sixth Ninth", new Integer[]{0, 3, 7, 9, 2});
        ret.put("Minor Thirteenth", new Integer[]{0, 3, 7, 10, 2, 5, 9});
        ret.put("Mu Chord", new Integer[]{0, 2, 4, 7});
        ret.put("Mystic Chord", new Integer[]{0, 6, 10, 4, 9, 2});
        ret.put("Ninth Augmented Fifth Chord", new Integer[]{0, 4, 8, 10, 2});
        ret.put("Ninth Flat Fifth Chord", new Integer[]{0, 4, 6, 10, 2});
        ret.put("Northern Lights Chord", new Integer[]{1, 2, 8, 0, 3, 6, 7, 10, 11, 4, 7});
        ret.put("Seven Six Chord", new Integer[]{0, 4, 7, 9, 10});
        ret.put("Seventh Suspension Four Chord", new Integer[]{0, 5, 7, 10});
        ret.put("So What Chord", new Integer[]{0, 5, 10, 3, 7});
        ret.put("Sus4 Chord", new Integer[]{0, 5, 7});
        ret.put("Sus2 Chord", new Integer[]{0, 2, 7});

        return ret;
    }

    public Map<String, Integer[]> createScalesMap() {
        Map<String, Integer[]> ret = new HashMap();

        ret.put("Ionian", new Integer[]{0, 2, 4, 5, 7, 9, 11});
        ret.put("Lydian", new Integer[]{0, 2, 4, 6, 7, 9, 11});
        ret.put("Mixolydian", new Integer[]{0, 2, 4, 5, 7, 9, 10});

        ret.put("Aeolian", new Integer[]{0, 2, 3, 5, 7, 8, 10});
        ret.put("Dorian", new Integer[]{0, 2, 3, 5, 7, 9, 10});
        ret.put("Phyrgian", new Integer[]{0, 1, 3, 5, 7, 8, 10});

        ret.put("Locrian", new Integer[]{0, 1, 3, 5, 6, 8, 10});

        ret.put("Ionian", new Integer[]{0, 2, 4, 7, 9});
        ret.put("Aeolian", new Integer[]{0, 3, 5, 7, 10});

        ret.put("Minor Blues Scale", new Integer[]{0, 3, 5, 6, 7, 10});

        return ret;
    }

    public String[] createIntervalsMap() {
        String[] ret = new String[12];
        ret[0] = "Root";
        ret[1] = "Minor Second";
        ret[2] = "Major Second";
        ret[3] = "Minor Third";
        ret[4] = "Major Third";
        ret[5] = "Perfect Fourth";
        ret[6] = "Tritone";
        ret[7] = "Perfect Fifth";
        ret[8] = "Minor Sixth";
        ret[9] = "Major Sixth";
        ret[10] = "Minor Seventh";
        ret[11] = "Major Seventh";
        return ret;
    }


}
