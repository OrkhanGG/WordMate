package utils.serialization.structures;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DictionaryAPIReader {

    public List<WordDefinition> Request(String json) {

        List<WordDefinition> wordDefinitions = new ArrayList<>();
        JsonArray allData = new JsonParser().parse(json).getAsJsonArray();
        JsonArray raw = allData.getAsJsonArray();
        if (raw != null) for (var mainElements : raw) {
            final String word = mainElements.getAsJsonObject().get("word").getAsString();
            WordDefinition wordDefinition = new WordDefinition();
            wordDefinition.word = word;
            JsonElement phonetics = mainElements.getAsJsonObject().get("phonetics");
            if (phonetics != null) {
                var _phonetic = wordDefinition.getNewPhonetic();
                wordDefinition.phonetics = new ArrayList<>();
                for (var phonetic : phonetics.getAsJsonArray()) {
                    // text audio sourceURL
                    final String text = phonetic.getAsJsonObject().get("text") != null ? phonetic.getAsJsonObject().get("text").getAsString() : null;
                    final String audio = phonetic.getAsJsonObject().get("audio") != null ? phonetic.getAsJsonObject().get("audio").getAsString() : null;
                    final String sourceURL = phonetic.getAsJsonObject().get("sourceUrl") != null ? phonetic.getAsJsonObject().get("sourceUrl").getAsString() : null;

                    _phonetic.text = text;
                    _phonetic.audio = audio;
                    _phonetic.sourceUrl = sourceURL;
                    wordDefinition.phonetics.add(_phonetic);
                }
            }
            JsonElement meanings = mainElements.getAsJsonObject().get("meanings");
            if (meanings != null) {
                var _meaning = wordDefinition.getNewMeaning();
                wordDefinition.meanings = new ArrayList<>();
                for (var meaning : meanings.getAsJsonArray()) {
                    final String partOfSpeech = meaning.getAsJsonObject().get("partOfSpeech") != null ? meaning.getAsJsonObject().get("partOfSpeech").getAsString() : null;
                    _meaning.partOfSpeech = partOfSpeech;
                    JsonElement definitions = meaning.getAsJsonObject().get("definitions");
                    if (definitions != null) {
                        var _definition = _meaning.getNewDefinition();
                        _definition.synonyms = new ArrayList<>();
                        _definition.antonyms = new ArrayList<>();
                        for (var definition : definitions.getAsJsonArray()) {
                            JsonElement _definition_obj = definition.getAsJsonObject().get("definition");
                            _meaning.definitions = new ArrayList<>();
                            if (_definition_obj != null) _definition.definition = _definition_obj.getAsString();
                            else _definition.definition = null;

                            JsonElement example = definition.getAsJsonObject().get("example");
                            if (example != null) _definition.example = _definition_obj.getAsString();
                            else _definition.example = null;

                            JsonElement definitionSynonyms = definition.getAsJsonObject().get("synonyms");
                            if (definitionSynonyms != null) {
                                for (var definitionSynonym : definitionSynonyms.getAsJsonArray()) {
                                    if (definitionSynonym != null)
                                        _definition.synonyms.add(definitionSynonym.getAsString());
                                }
                            }
                            JsonElement definitionAntonyms = definition.getAsJsonObject().get("antonyms");
                            if (definitionAntonyms != null) {
                                for (var definitionAntonym : definitionAntonyms.getAsJsonArray()) {
                                    if (definitionAntonym != null)
                                        _definition.antonyms.add(definitionAntonym.getAsString());
                                }
                            }
                            _meaning.definitions.add(_definition);
                        }
                    }
                }
                JsonElement synonyms = mainElements.getAsJsonObject().get("synonyms");
                if (synonyms != null) for (var synonym : synonyms.getAsJsonArray()) {
                    System.out.println(synonym);
                }
                JsonElement antonyms = mainElements.getAsJsonObject().get("antonyms");
                if (antonyms != null) for (var antonym : antonyms.getAsJsonArray()) {
                    System.out.println(antonym);
                }
                wordDefinition.meanings.add(_meaning);
            }
            wordDefinitions.add(wordDefinition);
        }

//        for (var word : wordDefinitions) {
//            System.out.println(word.word);
//            if (word.phonetics.size() > 0) for (var phonetic : word.phonetics) {
//                System.out.println("NEW PHONETIC");
//                System.out.println("\t phonetic text:" + phonetic.text);
//                System.out.println("\t phonetic audio:" + phonetic.audio);
//                System.out.println("\t phonetic sourceURL:" + phonetic.sourceUrl);
//            }
//            if (word.meanings.size() > 0) for (var meaning : word.meanings) {
//                System.out.println("\t\tmeaning-> part of speech:" + meaning.partOfSpeech);
//                for (var defs : meaning.definitions) {
//                    System.out.println("\t\t\tmeaning->definition def:" + defs.definition);
//                    System.out.println("\t\t\texample:" + defs.example);
//                }
//            }
//        }

        return wordDefinitions;
    }

    public JPanel CreateDefinitionStructureJPanel(List<WordDefinition> definitionStructure){
        JPanel  panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        Font headerFont = new Font("Courier", Font.BOLD,15);
        Font labelFont = new Font("Courier", Font.BOLD,15);
        Font descriptionFont = new Font("Courier", Font.PLAIN,12);

        for (var wordDefinition : definitionStructure) {
            JLabel wordLabel = new JLabel(wordDefinition.word);
            wordLabel.setFont(headerFont);
            panel.add(wordLabel);

            if (wordDefinition.meanings.size() > 0) for (var meaning : wordDefinition.meanings) {

                JLabel partOfSpeech = new JLabel("Part of speech: "+meaning.partOfSpeech);
                partOfSpeech.setFont(labelFont);
                panel.add(partOfSpeech);

                for (var defs : meaning.definitions) {
                    JLabel meaningDef = new JLabel("Definition: "+defs.definition);
                    JLabel exampleDef = new JLabel("Example"+defs.example);
                    meaningDef.setFont(labelFont);
                    exampleDef.setFont(labelFont);

                    panel.add(meaningDef);
                    panel.add(exampleDef);
                }
            }

            if (wordDefinition.phonetics.size() > 0) for (var phonetic : wordDefinition.phonetics) {
                JLabel _phonetic_ = new JLabel("Phonetics:");
                _phonetic_.setFont(headerFont);

                JLabel text = new JLabel("Text: "+phonetic.text);
                JLabel audio = new JLabel("Audio: "+ phonetic.audio);
                JLabel url = new JLabel("URL: "+ phonetic.sourceUrl);
                text.setFont(descriptionFont);
                audio.setFont(descriptionFont);
                url.setFont(descriptionFont);

                panel.add(text);
                panel.add(audio);
                panel.add(url);
            }
        }

        return panel;
    }
}