/*
 * Copyright (C) 2007-2015 Peter Monks.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * This file is part of an unsupported extension to Alfresco.
 * 
 */

package org.alfresco.extension.bulkimport.source;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import org.alfresco.service.cmr.repository.ContentWriter;


/**
 * This interface represents a "bulk import item" - a series of versions that
 * will be imported as a single node (content OR space) into the repository.
 * 
 * Notes:
 * <ol>
 * <li>Implementations of this interface must be as lightweight as possible, as
 * there may be many instances of these in memory at once.  For this reason
 * it is recommended that implementations only maintain references to the underlying
 * content and metadata sources (e.g. <code>java.io.File</code> objects, rather
 * than <code>InputStreams</code> or a <code>Map</code> containing metadata).</li>
 * <li>The import tool will call the putContent and getMetadata methods at most once,
 * allowing implementations to convert those references to actual values exactly once,
 * without retaining those values.</li>
 * </ol>
 *
 * @author Peter Monks (pmonks@gmail.com)
 */
public interface BulkImportItem
{
    /**
     * @return The path (delimited by '/' characters), relative to the root of the source, of this item's parent <i>(null indicates that the parent is the root of the source)</i>.
     */
    public String getRelativePathOfParent();
    
    /**
     * @return The parent association type to use for this item <i>(may be null)</i>.
     */
    public String getParentAssoc();
    
    /**
     * @return The namespace for the item <i>(may be null)</i>.
     */
    public String getNamespace();
    
    /**
     * @return The name of the item as it is / should be in the repository <i>(must not be null, empty or blank, and must meet Alfresco's naming rules for nodes)</i>.
     */
    public String getName();
    
    /**
     * @return True if this item is a directory (cm:folder or descendent type), false otherwise.
     */
    public boolean isDirectory();
    
    /**
     * @return The size (in bytes) of this item, defined as the size of all content (_not_ metadata!) files in all versions (will usually be 0 for directories).
     */
    public long sizeInBytes();
    
    /**
     * @return The number of versions in this item. Should normally be implemented as return(getVersions().size()).
     */
    public int numberOfVersions();
    
    /**
     * @return The number of aspects in this item, in all versions.
     */
    public int numberOfAspects();
    
    /**
     * @return The number of metadata properties in this item, in all versions.
     */
    public int numberOfMetadataProperties();
    
    /**
     * @return The set of versions comprising this item, sorted by version number <i>(must not be null or empty, and should contain only one entry if this item is a directory)</i>.
     */
    public SortedSet<Version> getVersions();

    
    /**
     * This interface identifies a single version within an importable item.
     * 
     * Invariants:
     * * hasContent() || hasMetadata() == true
     *
     * @author Peter Monks (pmonks@gmail.com)
     *
     */
    public interface Version
    {
        /**
         * @return Returns the decimal representation of the version number <i>(can be null if this version doesn't have a version number)</i>.
         */
        public BigDecimal getVersionNumber();

        /**
         * @return The type for this version <i>(may be null, but must be a valid Alfresco type if not null)</i>.
         */
        public String getType();
        
        /**
         * @return The aspect(s) for this version <i>(may be null or empty)</i>.
         */
        public Set<String> getAspects();
        
        /**
         * @return True if this version has content, false if not.
         */
        public boolean hasContent();
        
        /**
         * @return A reference to the source of the content (used in error messages) <i>(may be null if hasContent() = false)</i>.
         */
        public String getContentSource();
        
        /**
         * @return The size (in bytes) of this version, defined as the size of the content (_not_ metadata!) file (will usually be 0 for directories).
         */
        public long sizeInBytes();
        
        /**
         * @return True if the content is already in-place (in which case the content url property must be returned from the <code>getMetadata</code> call).
         */
        public boolean contentIsInPlace();
        
        /**
         * Called when the content of this version is ready to be streamed into the repository.
         * 
         * Notes:
         * <ol>
         * <li>This method is not called if contentIsInPlace() returns true.</li>
         * <li>It is the implementer's responsibility to set the MIME type, encoding and/or locale of the content being written.
         * Neither the import tool nor Alfresco will "guess" these values.</li>
         * </ol>
         * 
         * @param writer The ContentWriter to use for this version <i>(will not be null)</i>.
         */
        public void putContent(ContentWriter writer);
        
        /**
         * @return True if this version has metadata, false if not.
         */
        public boolean hasMetadata();

        /**
         * @return A reference to the source of the metadata (used in error messages) <i>(may be null if hasMetadata() = false)</i>.
         */
        public String getMetadataSource();
        
        /**
         * @return The metadata of this version, if any <i>(may return null or empty)</i>.
         */
        public Map<String,Serializable> getMetadata();
    }
}    