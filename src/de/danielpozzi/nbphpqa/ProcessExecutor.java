/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.danielpozzi.nbphpqa;

import java.io.Reader;
import org.netbeans.api.extexecution.ExternalProcessBuilder;
import org.netbeans.api.extexecution.ExecutionDescriptor;
import org.netbeans.api.extexecution.ExecutionService;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutionException;
import org.openide.util.Exceptions;

/**
 * Small utility class that is used to execute an external process.
 */
class ProcessExecutor 
{
    
    /**
     * Executes the given process and returns a reader instance with the
     * STDOUT result of the process.
     *
     * @param builder A configured process builder instance.
     *
     * @return Reader
     */
    public Reader execute(ExternalProcessBuilder builder) {
        Output output = new Output();

        ExecutionDescriptor descriptor = new ExecutionDescriptor().frontWindow(false).controllable(false).outProcessorFactory(output);

        ExecutionService service = ExecutionService.newService(builder, descriptor, "PHP Coding Standards");
        Future<Integer> task = service.run();
        try {
            task.get();
        } catch (InterruptedException ex) {
            Exceptions.printStackTrace(ex);
        } catch (ExecutionException ex) {
            Exceptions.printStackTrace(ex);
        }

        return output.getReader();
    }
}
