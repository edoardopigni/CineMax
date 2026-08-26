package cinemax;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Un oggetto della classe {@code OrarioDoc} rappresenta un orario, nel formato ore e minuti.
 * <p>
 * Consente di gestire il tempo, confrontare orari, calcolare differenze temporali 
 * e formattare la visualizzazione (a 12 o 24 ore). Implementa {@link Serializable}.
 * </p>
 * 
 * @author Daniele Paoli
 * @author Edoardo Pigni
 * @author Anes Khaia
 * @version 1.0
 */
public class OrarioDoc implements Serializable {
    
    // --- CAMPI ---

    /**
     * Le ore dell'orario (formato 0-23).
     */
    private int hh;

    /**
     * I minuti dell'orario (formato 0-59).
     */
    private int mm;

    /**
     * Il carattere separatore utilizzato tra ore e minuti per la formattazione testuale.
     */
    private static char sep = ':';

    /**
     * Flag che indica se il formato di visualizzazione attivo è a 24 ore (true) o a 12 ore (false).
     */
    private static boolean format24 = true;
    
    // --- COSTRUTTORI ---
    
    /**
     * Costruisce un oggetto che rappresenta l'orario attuale, 
     * cioè l'orario relativo all'istante esatto in cui viene invocato.
     */
    public OrarioDoc() {
        GregorianCalendar now = new GregorianCalendar();
        hh = now.get(Calendar.HOUR_OF_DAY);
        mm = now.get(Calendar.MINUTE);
    }
    
    /**
     * Costruisce un oggetto che rappresenta l'orario, in cui le ore 
     * sono date dal primo parametro e i minuti dal secondo.
     * 
     * @param hh le ore da impostare
     * @param mm i minuti da impostare
     */
    public OrarioDoc(int hh, int mm) {
        this.hh = hh;
        this.mm = mm;
    }
    
    /**
     * Costruisce un oggetto che rappresenta l'orario indicato 
     * nella stringa fornita tramite il parametro.
     * 
     * @param s la stringa che rappresenta l'orario, formattata strettamente nel formato "hh:mm"
     */
    public OrarioDoc(String s) {
        hh = Integer.parseInt(s.substring(0,2));
        mm = Integer.parseInt(s.substring(3,5));
    }

    // --- METODI ---
    
    /**
     * Restituisce le ore dell'orario.
     * 
     * @return le ore (tra 0 e 23)
     */
    public int getOre() {
        return hh;
    }
    
    /**
     * Restituisce i minuti dell'orario.
     * 
     * @return i minuti (tra 0 e 59)
     */
    public int getMinuti() {
        return mm;
    }

    /**
     * Imposta il separatore testuale tra ore e minuti.
     * 
     * @param sep il carattere da usare come separatore (es. ':')
     */
    public void setSeparatore(char sep) {
        this.sep = sep;
    }
    
    /**
     * Imposta il formato di visualizzazione dell'orario.
     * 
     * @param format {@code true} per attivare il formato a 24 ore, {@code false} per il formato a 12 ore (AM/PM)
     */
    public static void setFormat24(boolean format) {
        format24 = format;
    }

    /**
     * Verifica se il formato di visualizzazione attivo è quello a 24 ore.
     * 
     * @return {@code true} se il formato attivo è da 24 ore, {@code false} altrimenti
     */
    public static boolean isFormato24Attivo() {
        return format24;
    }
    
    /**
     * Verifica se l'orario corrente è identico a un altro orario fornito.
     * 
     * @param altro l'oggetto {@code OrarioDoc} da confrontare
     * @return {@code true} se le ore e i minuti coincidono, {@code false} altrimenti
     */
    public boolean equals(OrarioDoc altro) {
        return this.hh == altro.hh && this.mm == altro.mm;
    }
    
    /**
     * Verifica l'uguaglianza tra l'orario corrente e un oggetto generico.
     * 
     * @param altro l'oggetto da confrontare
     * @return {@code true} se l'oggetto fornito è un {@code OrarioDoc} e ha lo stesso orario, {@code false} altrimenti
     */
    @Override
    public boolean equals(Object altro) {
        if(altro instanceof OrarioDoc)
            return this.equals((OrarioDoc)altro);
        else return false;
    }
    
    /**
     * Determina se l'orario corrente precede temporalmente l'orario fornito.
     * 
     * @param altro l'orario con cui effettuare il confronto
     * @return {@code true} se l'orario corrente è minore, {@code false} altrimenti
     */
    public boolean isMinore(OrarioDoc altro) {
        return hh < altro.hh ||
                (hh == altro.hh && mm < altro.mm);
    }
    
    /**
     * Determina se l'orario corrente è successivo temporalmente all'orario fornito.
     * 
     * @param altro l'orario con cui effettuare il confronto
     * @return {@code true} se l'orario corrente è maggiore, {@code false} altrimenti
     */
    public boolean isMaggiore(OrarioDoc altro) {
        return hh > altro.hh ||
                (hh == altro.hh && mm > altro.mm);
    }
    
    /**
     * Restituisce il numero di minuti che intercorrono tra l'orario rappresentato dall'oggetto 
     * che esegue il metodo e quello rappresentato dall'oggetto fornito tramite il parametro, 
     * considerati come orari riferiti alla stessa giornata.
     * <p>
     * Se l'orario rappresentato dall'oggetto che esegue il metodo è minore di quello fornito 
     * tramite il parametro, il risultato sarà un numero positivo; se invece è maggiore, 
     * il risultato sarà un numero negativo.
     * </p>
     * 
     * @param altro l'orario di cui calcolare la differenza in minuti
     * @return l'intero che rappresenta quanto manca all'orario indicato
     */
    public int quantoManca(OrarioDoc altro) {
        return (altro.hh - hh) * 60 + altro.mm - mm;
    }
    
    /**
     * Restituisce una rappresentazione in forma di stringa dell'orario, formattata in base 
     * alle impostazioni di separatore e al formato (12 o 24 ore).
     * 
     * @return la stringa formattata dell'orario (es. "14:30" oppure "2:30pm")
     */
    @Override
    public String toString() {
        String stringaMinuti = (mm < 10 ? "0" : "") + mm;
        if (format24)
            return String.valueOf(hh) + sep + stringaMinuti;
        else {
            int oraRisultato;
            String suff;
            if (hh == 0) {
                oraRisultato = 12;
                suff = "am";
            } else if (hh > 0 && hh < 12) {
                oraRisultato = hh;
                suff = "am";
            } else if (hh == 12) {
                oraRisultato = 12;
                suff = "pm";
            } else {
                oraRisultato = hh - 12;
                suff = "pm";
            }
            return String.valueOf(oraRisultato) + sep
            + stringaMinuti + suff;
        }
    }
}