package telran.git.project;

import java.io.*;
import java.util.logging.*;

public class SourseDestination {
		static Logger LOG = Logger.getLogger("logger");//String for Logger identification
		
		public static void main(String[] args) {
			Handler handler = new ConsoleHandler();
			LOG.addHandler(handler);
			handler.setLevel(Level.SEVERE);
			LOG.setLevel(Level.SEVERE);
			
			
			try {
				if (args.length > 1) {
					LOG.fine(String.format(null, args));
					try (Reader reader = args[0].equalsIgnoreCase("console") ? new InputStreamReader(System.in)
							: new FileReader(args[0]);
							PrintWriter writer = args[1].equalsIgnoreCase("console")
									? new PrintWriter(new OutputStreamWriter(System.out))
									: new PrintWriter(args[1]);) {
						sourseToDestination(reader, writer);
					}
				} else {
					System.out.println("too few arguments");
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

		}

		private static void sourseToDestination(Reader reader, PrintWriter stream) throws IOException {
			BufferedReader bufferedReader = new BufferedReader(reader);
			while (true) {
				String line = bufferedReader.readLine();
				if (line == null) {
					break;
				}
				stream.println(line);
			}
		}
	}
	
	

