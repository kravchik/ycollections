/* Generated By:JavaCC: Do not edit this line. YADSParser.java */
package yk.lang.yads;
import yk.jcommon.collections.Tuple;
import yk.jcommon.collections.YList;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;

import static yk.jcommon.collections.YArrayList.al;

public class YADSParser implements YADSParserConstants {
    public static YList parseList(String s) {
        try {
            return new YADSParser(new ByteArrayInputStream(s.getBytes("UTF-8"))).yadList();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object parseClass(String s) {
        try {
            return new YADSParser(new ByteArrayInputStream(s.getBytes("UTF-8"))).yadClass();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

  final public YList yadList() throws ParseException {
 YList result = al();String key; Object value;
    label_1:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case LPAREN:
      case TRUE:
      case FALSE:
      case INTEGER_LITERAL:
      case FLOATING_POINT_LITERAL:
      case STRING_LITERAL:
      case STRING_LITERAL_2:
      case IDENTIFIER:
        ;
        break;
      default:
        jj_la1[0] = jj_gen;
        break label_1;
      }
      if (jj_2_1(2)) {
        key = jj_consume_token(IDENTIFIER).image;
        jj_consume_token(27);
        value = yadElement1();
                                result.add(new Tuple(key, value));
      } else {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case LPAREN:
        case TRUE:
        case FALSE:
        case INTEGER_LITERAL:
        case FLOATING_POINT_LITERAL:
        case STRING_LITERAL:
        case STRING_LITERAL_2:
        case IDENTIFIER:
          value = yadElement1();
                                    result.add(value);
          break;
        default:
          jj_la1[1] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
      }
    }
     {if (true) return result;}
    throw new Error("Missing return statement in function");
  }

  final public Object yadElement1() throws ParseException {
 Object value;
    //LOOKAHEAD(2)
        value = commedClass();
     {if (true) return value;}
    throw new Error("Missing return statement in function");
  }

  final public Object yadElement() throws ParseException {
 Object o;String s;
    if (jj_2_2(2)) {
      o = yadClass();
    } else {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case INTEGER_LITERAL:
        s = jj_consume_token(INTEGER_LITERAL).image;
                                 if (s.toLowerCase().endsWith("l")) o = Long.parseLong(s.substring(0, s.length()-1)); else o = Integer.parseInt(s);
        break;
      case FLOATING_POINT_LITERAL:
        s = jj_consume_token(FLOATING_POINT_LITERAL).image;
                                        if (s.toLowerCase().endsWith("d")) o = Double.parseDouble(s.substring(0, s.length()-1)); else o = Float.parseFloat(s);
        break;
      case STRING_LITERAL:
        s = jj_consume_token(STRING_LITERAL).image;
                                o = s.substring(1, s.length() - 1);
        break;
      case STRING_LITERAL_2:
        s = jj_consume_token(STRING_LITERAL_2).image;
                                  o = s.substring(1, s.length() - 1);
        break;
      case IDENTIFIER:
        s = jj_consume_token(IDENTIFIER).image;
                            if ("null".equals(s)) o = null; else o = s;
        break;
      case TRUE:
        jj_consume_token(TRUE);
            o = true;
        break;
      case FALSE:
        jj_consume_token(FALSE);
             o = false;
        break;
      default:
        jj_la1[2] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
     {if (true) return "null".equals(o) ? null : o;}
    throw new Error("Missing return statement in function");
  }

  final public Object commedClass() throws ParseException {
 YList values = al(); Object o;
    o = yadElement();
                       values.add(o);
    label_2:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case COMMA:
        ;
        break;
      default:
        jj_la1[3] = jj_gen;
        break label_2;
      }
      jj_consume_token(COMMA);
      o = yadElement();
                       values.add(o);
    }
     {if (true) return values.size() > 1 ? new YADClass(null, values) : values.get(0);}
    throw new Error("Missing return statement in function");
  }

  final public YADClass yadClass() throws ParseException {
 String s = null;YList l;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case IDENTIFIER:
      s = jj_consume_token(IDENTIFIER).image;
      break;
    default:
      jj_la1[4] = jj_gen;
      ;
    }
    jj_consume_token(LPAREN);
    l = yadList();
    jj_consume_token(RPAREN);
     {if (true) return new YADClass(s, l);}
    throw new Error("Missing return statement in function");
  }

  private boolean jj_2_1(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_1(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(0, xla); }
  }

  private boolean jj_2_2(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_2(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(1, xla); }
  }

  private boolean jj_3R_16() {
    if (jj_scan_token(FALSE)) return true;
    return false;
  }

  private boolean jj_3R_15() {
    if (jj_scan_token(TRUE)) return true;
    return false;
  }

  private boolean jj_3R_3() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_scan_token(24)) jj_scanpos = xsp;
    if (jj_scan_token(LPAREN)) return true;
    if (jj_3R_4()) return true;
    if (jj_scan_token(RPAREN)) return true;
    return false;
  }

  private boolean jj_3R_14() {
    if (jj_scan_token(IDENTIFIER)) return true;
    return false;
  }

  private boolean jj_3R_7() {
    if (jj_3R_8()) return true;
    return false;
  }

  private boolean jj_3R_13() {
    if (jj_scan_token(STRING_LITERAL_2)) return true;
    return false;
  }

  private boolean jj_3R_12() {
    if (jj_scan_token(STRING_LITERAL)) return true;
    return false;
  }

  private boolean jj_3R_11() {
    if (jj_scan_token(FLOATING_POINT_LITERAL)) return true;
    return false;
  }

  private boolean jj_3R_10() {
    if (jj_scan_token(INTEGER_LITERAL)) return true;
    return false;
  }

  private boolean jj_3R_6() {
    if (jj_3R_7()) return true;
    return false;
  }

  private boolean jj_3_2() {
    if (jj_3R_3()) return true;
    return false;
  }

  private boolean jj_3R_9() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3_2()) {
    jj_scanpos = xsp;
    if (jj_3R_10()) {
    jj_scanpos = xsp;
    if (jj_3R_11()) {
    jj_scanpos = xsp;
    if (jj_3R_12()) {
    jj_scanpos = xsp;
    if (jj_3R_13()) {
    jj_scanpos = xsp;
    if (jj_3R_14()) {
    jj_scanpos = xsp;
    if (jj_3R_15()) {
    jj_scanpos = xsp;
    if (jj_3R_16()) return true;
    }
    }
    }
    }
    }
    }
    }
    return false;
  }

  private boolean jj_3R_8() {
    if (jj_3R_9()) return true;
    return false;
  }

  private boolean jj_3_1() {
    if (jj_scan_token(IDENTIFIER)) return true;
    if (jj_scan_token(27)) return true;
    return false;
  }

  private boolean jj_3R_5() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3_1()) {
    jj_scanpos = xsp;
    if (jj_3R_6()) return true;
    }
    return false;
  }

  private boolean jj_3R_4() {
    Token xsp;
    while (true) {
      xsp = jj_scanpos;
      if (jj_3R_5()) { jj_scanpos = xsp; break; }
    }
    return false;
  }

  /** Generated Token Manager. */
  public YADSParserTokenManager token_source;
  JavaCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private Token jj_scanpos, jj_lastpos;
  private int jj_la;
  private int jj_gen;
  final private int[] jj_la1 = new int[5];
  static private int[] jj_la1_0;
  static {
      jj_la1_init_0();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x1d1c200,0x1d1c200,0x1d1c000,0x2000,0x1000000,};
   }
  final private JJCalls[] jj_2_rtns = new JJCalls[2];
  private boolean jj_rescan = false;
  private int jj_gc = 0;

  /** Constructor with InputStream. */
  public YADSParser(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public YADSParser(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new JavaCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new YADSParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 5; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 5; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor. */
  public YADSParser(java.io.Reader stream) {
    jj_input_stream = new JavaCharStream(stream, 1, 1);
    token_source = new YADSParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 5; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 5; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor with generated Token Manager. */
  public YADSParser(YADSParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 5; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(YADSParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 5; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      if (++jj_gc > 100) {
        jj_gc = 0;
        for (int i = 0; i < jj_2_rtns.length; i++) {
          JJCalls c = jj_2_rtns[i];
          while (c != null) {
            if (c.gen < jj_gen) c.first = null;
            c = c.next;
          }
        }
      }
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }

  static private final class LookaheadSuccess extends java.lang.Error { }
  final private LookaheadSuccess jj_ls = new LookaheadSuccess();
  private boolean jj_scan_token(int kind) {
    if (jj_scanpos == jj_lastpos) {
      jj_la--;
      if (jj_scanpos.next == null) {
        jj_lastpos = jj_scanpos = jj_scanpos.next = token_source.getNextToken();
      } else {
        jj_lastpos = jj_scanpos = jj_scanpos.next;
      }
    } else {
      jj_scanpos = jj_scanpos.next;
    }
    if (jj_rescan) {
      int i = 0; Token tok = token;
      while (tok != null && tok != jj_scanpos) { i++; tok = tok.next; }
      if (tok != null) jj_add_error_token(kind, i);
    }
    if (jj_scanpos.kind != kind) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) throw jj_ls;
    return false;
  }


/** Get the next Token. */
  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;
  private int[] jj_lasttokens = new int[100];
  private int jj_endpos;

  private void jj_add_error_token(int kind, int pos) {
    if (pos >= 100) return;
    if (pos == jj_endpos + 1) {
      jj_lasttokens[jj_endpos++] = kind;
    } else if (jj_endpos != 0) {
      jj_expentry = new int[jj_endpos];
      for (int i = 0; i < jj_endpos; i++) {
        jj_expentry[i] = jj_lasttokens[i];
      }
      jj_entries_loop: for (java.util.Iterator<?> it = jj_expentries.iterator(); it.hasNext();) {
        int[] oldentry = (int[])(it.next());
        if (oldentry.length == jj_expentry.length) {
          for (int i = 0; i < jj_expentry.length; i++) {
            if (oldentry[i] != jj_expentry[i]) {
              continue jj_entries_loop;
            }
          }
          jj_expentries.add(jj_expentry);
          break jj_entries_loop;
        }
      }
      if (pos != 0) jj_lasttokens[(jj_endpos = pos) - 1] = kind;
    }
  }

  /** Generate ParseException. */
  public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[28];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 5; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 28; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    jj_endpos = 0;
    jj_rescan_token();
    jj_add_error_token(0, 0);
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

  private void jj_rescan_token() {
    jj_rescan = true;
    for (int i = 0; i < 2; i++) {
    try {
      JJCalls p = jj_2_rtns[i];
      do {
        if (p.gen > jj_gen) {
          jj_la = p.arg; jj_lastpos = jj_scanpos = p.first;
          switch (i) {
            case 0: jj_3_1(); break;
            case 1: jj_3_2(); break;
          }
        }
        p = p.next;
      } while (p != null);
      } catch(LookaheadSuccess ls) { }
    }
    jj_rescan = false;
  }

  private void jj_save(int index, int xla) {
    JJCalls p = jj_2_rtns[index];
    while (p.gen > jj_gen) {
      if (p.next == null) { p = p.next = new JJCalls(); break; }
      p = p.next;
    }
    p.gen = jj_gen + xla - jj_la; p.first = token; p.arg = xla;
  }

  static final class JJCalls {
    int gen;
    Token first;
    int arg;
    JJCalls next;
  }

    //public static <T> T parseClass(Class<? extends T> clazz, String s) {
    //    try {
    //        return new YADSParser(new ByteArrayInputStream(s.getBytes("UTF-8"))).yadClass();
    //    } catch (UnsupportedEncodingException | ParseException e) {
    //        throw new RuntimeException(e);
    //    }
    //}
    //

    //TODO parseClass


}
