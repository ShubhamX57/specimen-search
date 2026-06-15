package io.shinde.specimensearch.model;

public record Specimen(
        String id,
        String name,
        String taxon,
        String tissue,
        String keywords
) {
    public String searchableText() {
        return (name + " " + taxon + " " + tissue + " " + keywords).toLowerCase();
    }
}
