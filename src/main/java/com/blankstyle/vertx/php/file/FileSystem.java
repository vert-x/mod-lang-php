package com.blankstyle.vertx.php.file;

import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.file.FileProps;
import org.vertx.java.core.file.FileSystemProps;

import com.blankstyle.vertx.php.Handler;

import com.caucho.quercus.annotation.Optional;
import com.caucho.quercus.env.BooleanValue;
import com.caucho.quercus.env.Callback;
import com.caucho.quercus.env.NumberValue;
import com.caucho.quercus.env.StringValue;
import com.caucho.quercus.env.Env;

/**
 * A PHP compatible implementation of the Vert.x FileSystem.
 */
public final class FileSystem {

  private org.vertx.java.core.file.FileSystem fileSystem;

  public FileSystem(org.vertx.java.core.file.FileSystem fileSystem) {
    this.fileSystem = fileSystem;
  }

  /**
   * Executes an asynchronous chmod call.
   */
  public FileSystem chmod(final Env env, StringValue path, StringValue perms, @Optional StringValue dirPerms, final Callback handler) {
    if (dirPerms != null && !dirPerms.isDefault()) {
      fileSystem.chmod(path.toString(), perms.toString(), dirPerms.toString(), new Handler<AsyncResult<Void>>(env, handler));
    }
    else {
      fileSystem.chmod(path.toString(), perms.toString(), new Handler<AsyncResult<Void>>(env, handler));
    }
    return this;
  }

  /**
   * Executes a synchronous chmod call.
   */
  public FileSystem chmodSync(Env env, StringValue path, StringValue perms, @Optional StringValue dirPerms) {
    if (dirPerms != null && !dirPerms.isDefault()) {
      fileSystem.chmodSync(path.toString(), perms.toString(), dirPerms.toString());
    }
    else {
      fileSystem.chmodSync(path.toString(), perms.toString());
    }
    return this;
  }

  /**
   * Executes an asynchronous copy call.
   */
  public FileSystem copy(final Env env, StringValue from, StringValue to, @Optional BooleanValue recursive, final Callback handler) {
    if (recursive != null && !recursive.isDefault()) {
      fileSystem.copy(from.toString(), to.toString(), recursive.toBoolean(), new Handler<AsyncResult<Void>>(env, handler));
    }
    else {
      fileSystem.copy(from.toString(), to.toString(), new Handler<AsyncResult<Void>>(env, handler));
    }
    return this;
  }

  /**
   * Executes a synchronous copy call.
   */
  public FileSystem copySync(Env env, StringValue from, StringValue to, @Optional BooleanValue recursive) {
    if (recursive != null && !recursive.isDefault()) {
      fileSystem.copySync(from.toString(), to.toString(), recursive.toBoolean());
    }
    else {
      fileSystem.copySync(from.toString(), to.toString());
    }
    return this;
  }

  /**
   * Executes an asynchronous create file call.
   */
  public FileSystem createFile(final Env env, StringValue path, @Optional StringValue perms, final Callback handler) {
    if (perms != null && !perms.isDefault()) {
      fileSystem.createFile(path.toString(), perms.toString(), new Handler<AsyncResult<Void>>(env, handler));
    }
    else {
      fileSystem.createFile(path.toString(), new Handler<AsyncResult<Void>>(env, handler));
    }
    return this;
  }

  /**
   * Executes a synchronous create file call.
   */
  public FileSystem createFileSync(Env env, StringValue path, @Optional StringValue perms) {
    if (perms != null && !perms.isDefault()) {
      fileSystem.createFileSync(path.toString(), perms.toString());
    }
    else {
      fileSystem.createFileSync(path.toString());
    }
    return this;
  }

  /**
   * Executes an asynchronous delete call.
   */
  public FileSystem delete(final Env env, StringValue path, @Optional BooleanValue recursive, final Callback handler) {
    if (recursive != null && !recursive.isDefault()) {
      fileSystem.delete(path.toString(), recursive.toBoolean(), new Handler<AsyncResult<Void>>(env, handler));
    }
    else {
      fileSystem.delete(path.toString(), new Handler<AsyncResult<Void>>(env, handler));
    }
    return this;
  }

  /**
   * Executes a synchronous delete call.
   */
  public FileSystem deleteSync(Env env, StringValue path, @Optional BooleanValue recursive) {
    if (recursive != null && !recursive.isDefault()) {
      fileSystem.deleteSync(path.toString(), recursive.toBoolean());
    }
    else {
      fileSystem.deleteSync(path.toString());
    }
    return this;
  }

  /**
   * Executes an asynchronous exists call.
   */
  public FileSystem exists(final Env env, StringValue path, final Callback handler) {
    fileSystem.exists(path.toString(), new Handler<AsyncResult<Boolean>>(env, handler));
    return this;
  }

  /**
   * Executes a synchronous exists call.
   */
  public boolean existsSync(Env env, StringValue path) {
    return fileSystem.existsSync(path.toString());
  }

  /**
   * Executes an asynchronous fsprops call.
   */
  public FileSystem fsProps(final Env env, StringValue path, final Callback handler) {
    fileSystem.fsProps(path.toString(), new Handler<AsyncResult<FileSystemProps>>(env, handler));
    return this;
  }

  /**
   * Executes a synchronous fsprops call.
   */
  public FileSystemProps fsPropsSync(Env env, StringValue path) {
    return fileSystem.fsPropsSync(path.toString());
  }

  /**
   * Executes an asynchronous link call.
   */
  public FileSystem link(final Env env, StringValue link, StringValue existing, final Callback handler) {
    fileSystem.link(link.toString(), existing.toString(), new Handler<AsyncResult<Void>>(env, handler));
    return this;
  }

  /**
   * Executes a synchronous link call.
   */
  public FileSystem linkSync(Env env, StringValue link, StringValue existing) {
    fileSystem.linkSync(link.toString(), existing.toString());
    return this;
  }

  /**
   * Executes an asynchronous lprops call.
   */
  public FileSystem lprops(final Env env, StringValue path, final Callback handler) {
    fileSystem.lprops(path.toString(), new Handler<AsyncResult<FileProps>>(env, handler));
    return this;
  }

  /**
   * Executes a synchronous lprops call.
   */
  public FileSystem lpropsSync(Env env, StringValue path) {
    fileSystem.lpropsSync(path.toString());
    return this;
  }

  /**
   * Executes an asynchronous mkdir call.
   */
  public FileSystem mkdir(final Env env, StringValue path, @Optional StringValue perms, @Optional BooleanValue createParents, final Callback handler) {
    if (perms != null && !perms.isDefault()) {
      if (createParents != null && !createParents.isDefault()) {
        fileSystem.mkdir(path.toString(), perms.toString(), createParents.toBoolean(), new Handler<AsyncResult<Void>>(env, handler));
      }
      else {
        fileSystem.mkdir(path.toString(), perms.toString(), new Handler<AsyncResult<Void>>(env, handler));
      }
    }
    else if (createParents != null && !createParents.isDefault()) {
      fileSystem.mkdir(path.toString(), createParents.toBoolean(), new Handler<AsyncResult<Void>>(env, handler));
    }
    else {
      fileSystem.mkdir(path.toString(), new Handler<AsyncResult<Void>>(env, handler));
    }
    return this;
  }

  /**
   * Executes a synchronous mkdir call.
   */
  public FileSystem mkdirSync(Env env, StringValue path, @Optional StringValue perms, @Optional BooleanValue createParents) {
    if (perms != null && !perms.isDefault()) {
      if (createParents != null && !createParents.isDefault()) {
        fileSystem.mkdirSync(path.toString(), perms.toString(), createParents.toBoolean());
      }
      else {
        fileSystem.mkdirSync(path.toString(), perms.toString());
      }
    }
    else if (createParents != null && !createParents.isDefault()) {
      fileSystem.mkdirSync(path.toString(), createParents.toBoolean());
    }
    else {
      fileSystem.mkdirSync(path.toString());
    }
    return this;
  }

  /**
   * Executes an asynchronous move call.
   */
  public FileSystem move(final Env env, StringValue from, StringValue to, final Callback handler) {
    fileSystem.move(from.toString(), to.toString(), new Handler<AsyncResult<Void>>(env, handler));
    return this;
  }

  /**
   * Executes a synchronous move call.
   */
  public FileSystem moveSync(Env env, StringValue from, StringValue to) {
    fileSystem.moveSync(from.toString(), to.toString());
    return this;
  }

  /**
   * Executes an asynchronous open call.
   */
  public FileSystem open(final Env env, StringValue path, @Optional StringValue perms, @Optional BooleanValue read, @Optional BooleanValue write, @Optional BooleanValue createNew, @Optional BooleanValue flush, Callback handler) {
    if ((perms != null && !perms.isNull()) && (read != null && !read.isNull()) && (write != null && !write.isNull()) && (createNew != null && !createNew.isNull()) && (flush != null && !flush.isNull())) {
      fileSystem.open(path.toString(), perms.toString(), read.toBoolean(), write.toBoolean(), createNew.toBoolean(), flush.toBoolean(), new Handler<AsyncResult<org.vertx.java.core.file.AsyncFile>>(env, handler));
    }
    else if ((perms != null && !perms.isDefault()) && (read != null && !read.isDefault()) && (write != null && !write.isDefault()) && (createNew != null && !createNew.isDefault())) {
      fileSystem.open(path.toString(), perms.toString(), read.toBoolean(), write.toBoolean(), createNew.toBoolean(), new Handler<AsyncResult<org.vertx.java.core.file.AsyncFile>>(env, handler));
    }
    else if ((perms != null && !perms.isDefault()) && (createNew != null && !createNew.isDefault())) {
      fileSystem.open(path.toString(), perms.toString(), createNew.toBoolean(), new Handler<AsyncResult<org.vertx.java.core.file.AsyncFile>>(env, handler));
    }
    else if (perms != null && !perms.isDefault()) {
      fileSystem.open(path.toString(), perms.toString(), new Handler<AsyncResult<org.vertx.java.core.file.AsyncFile>>(env, handler));
    }
    else {
      fileSystem.open(path.toString(), new Handler<AsyncResult<org.vertx.java.core.file.AsyncFile>>(env, handler));
    }
    return this;
  }

  /**
   * Executes a synchronous open call.
   */
  public org.vertx.java.core.file.AsyncFile openSync(Env env, StringValue path, @Optional StringValue perms, @Optional BooleanValue read, @Optional BooleanValue write, @Optional BooleanValue createNew, @Optional BooleanValue flush) {
    if ((perms != null && !perms.isNull()) && (read != null && !read.isNull()) && (write != null && !write.isNull()) && (createNew != null && !createNew.isNull()) && (flush != null && !flush.isNull())) {
      return fileSystem.openSync(path.toString(), perms.toString(), read.toBoolean(), write.toBoolean(), createNew.toBoolean(), flush.toBoolean());
    }
    else if ((perms != null && !perms.isDefault()) && (read != null && !read.isDefault()) && (write != null && !write.isDefault()) && (createNew != null && !createNew.isDefault())) {
      return fileSystem.openSync(path.toString(), perms.toString(), read.toBoolean(), write.toBoolean(), createNew.toBoolean());
    }
    else if ((perms != null && !perms.isDefault()) && (createNew != null && !createNew.isDefault())) {
      return fileSystem.openSync(path.toString(), perms.toString(), createNew.toBoolean());
    }
    else if (perms != null && !perms.isDefault()) {
      return fileSystem.openSync(path.toString(), perms.toString());
    }
    else {
      return fileSystem.openSync(path.toString());
    }
  }

  /**
   * Executes an asynchronous props call.
   */
  public FileSystem props(Env env, StringValue path, Callback handler) {
    fileSystem.props(path.toString(), new Handler<AsyncResult<FileProps>>(env, handler));
    return this;
  }

  /**
   * Executes a synchronous props call.
   */
  public FileSystem propsSync(Env env, StringValue path) {
    fileSystem.propsSync(path.toString());
    return this;
  }

  /**
   * Executes an asynchronous readdir call.
   */
  public FileSystem readDir(Env env, StringValue path, @Optional StringValue filter, Callback handler) {
    if (filter != null && !filter.isNull()) {
      fileSystem.readDir(path.toString(), filter.toString(), new Handler<AsyncResult<String[]>>(env, handler));
    }
    else {
      fileSystem.readDir(path.toString(), new Handler<AsyncResult<String[]>>(env, handler));
    }
    return this;
  }

  /**
   * Executes a synchronous readdir call.
   */
  public String[] readDirSync(Env env, StringValue path, @Optional StringValue filter) {
    if (filter != null && !filter.isDefault()) {
      return fileSystem.readDirSync(path.toString(), filter.toString());
    }
    else {
      return fileSystem.readDirSync(path.toString());
    }
  }

  /**
   * Executes an asynchronous readfile call.
   */
  public FileSystem readFile(Env env, StringValue path, Callback handler) {
    fileSystem.readFile(path.toString(), new Handler<AsyncResult<Buffer>>(env, handler));
    return this;
  }

  /**
   * Executes a synchronous readfile call.
   */
  public FileSystem readFileSync(Env env, StringValue path) {
    fileSystem.readFileSync(path.toString());
    return this;
  }

  /**
   * Executes an asynchronous read symlink call.
   */
  public FileSystem readSymlink(Env env, StringValue link, Callback handler) {
    fileSystem.readSymlink(link.toString(), new Handler<AsyncResult<String>>(env, handler));
    return this;
  }

  /**
   * Executes a synchronous read symlink call.
   */
  public String readSymlinkSync(Env env, StringValue link) {
    return fileSystem.readSymlinkSync(link.toString());
  }

  /**
   * Executes an asynchronous symlink call.
   */
  public FileSystem symlink(Env env, StringValue link, StringValue existing, Callback handler) {
    fileSystem.symlink(link.toString(), existing.toString(), new Handler<AsyncResult<Void>>(env, handler));
    return this;
  }

  /**
   * Executes a synchronous symlink call.
   */
  public FileSystem symlinkSync(Env env, StringValue link, StringValue existing) {
    fileSystem.symlinkSync(link.toString(), existing.toString());
    return this;
  }

  /**
   * Executes an asynchronous truncate call.
   */
  public FileSystem truncate(Env env, StringValue path, NumberValue len, Callback handler) {
    fileSystem.truncate(path.toString(), len.toLong(), new Handler<AsyncResult<Void>>(env, handler));
    return this;
  }

  /**
   * Executes a synchronous truncate call.
   */
  public FileSystem truncateSync(Env env, StringValue path, NumberValue len) {
    fileSystem.truncateSync(path.toString(), len.toLong());
    return this;
  }

  /**
   * Executes an asynchronous unlink call.
   */
  public FileSystem unlink(Env env, StringValue link, Callback handler) {
    fileSystem.unlink(link.toString(), new Handler<AsyncResult<Void>>(env, handler));
    return this;
  }

  /**
   * Executes a synchronous unlink call.
   */
  public FileSystem unlinkSync(Env env, StringValue link) {
    fileSystem.unlinkSync(link.toString());
    return this;
  }

  /**
   * Executes an asynchronous write file call.
   */
  public FileSystem writeFile(Env env, StringValue path, Buffer data, Callback handler) {
    fileSystem.writeFile(path.toString(), data, new Handler<AsyncResult<Void>>(env, handler));
    return this;
  }

  /**
   * Executes a synchronous write file call.
   */
  public FileSystem writeFileSync(Env env, StringValue path, Buffer data) {
    fileSystem.writeFileSync(path.toString(), data);
    return this;
  }

  public String toString() {
    return "php:Vertx\\FileSystem";
  }

}
