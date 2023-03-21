import { Editor } from "@toast-ui/react-editor";
import "@toast-ui/editor/dist/toastui-editor.css";
import { useRef } from "react";

const TextEditor = () => {

    const editorRef:any = useRef(null)

  return (
    <Editor
      ref={editorRef}
      initialValue=""
      previewStyle="tab"
      height="400px"
      useCommandShortcut={false}
      usageStatistics={false}
      onChange={() => {
        console.log(editorRef.current.getInstance().getMarkdown())
      }}
    />
  );
};

export default TextEditor;
