/*
 * NBPHPQAA
 */
package de.danielpozzi.nbphpqa;

import org.openide.filesystems.FileObject;
/**
 *
 * @author daniel
 */
public interface QATool
{
    public XmlLogResult execute(FileObject fo);
}
